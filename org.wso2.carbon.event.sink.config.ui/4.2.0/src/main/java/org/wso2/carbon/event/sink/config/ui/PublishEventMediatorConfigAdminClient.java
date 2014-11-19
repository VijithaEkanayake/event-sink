/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.event.sink.config.ui;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.sink.config.EventSink;
import org.wso2.carbon.event.sink.config.stub.PublishEventMediatorConfigAdminStub;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Admin client that uses the backend persistence and cryptographic facilities
 */
public class PublishEventMediatorConfigAdminClient {

    private static final Log log = LogFactory.getLog(PublishEventMediatorConfigAdminClient.class);
	private static final String BUNDLE = "org.wso2.carbon.event.sink.config.ui.i18n.Resources";
    private PublishEventMediatorConfigAdminStub stub;
	private ResourceBundle bundle;

    public PublishEventMediatorConfigAdminClient(String cookie, String backendServerURL,
                                                 ConfigurationContext configCtx, Locale locale) throws AxisFault {
        String serviceURL = backendServerURL + "PublishEventMediatorConfigAdmin";
        bundle = ResourceBundle.getBundle(BUNDLE, locale);

        stub = new PublishEventMediatorConfigAdminStub(configCtx, serviceURL);
        ServiceClient client = stub._getServiceClient();
        Options option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);
    }

    public boolean writeEventSinkXml(EventSink eventSink) throws RemoteException {
            stub.writeEventSink(eventSink.getName(),eventSink.getUsername(),eventSink.getPassword(), eventSink.getReceiverUrl(),eventSink.getAuthenticatorUrl());
            return true;
    }

    public org.wso2.carbon.event.sink.config.xsd.EventSink[] getAllEventSinks() throws RemoteException {
        org.wso2.carbon.event.sink.config.xsd.EventSink[] eventSinkList = stub.getAllEventSinks();
        return eventSinkList == null ? new org.wso2.carbon.event.sink.config.xsd.EventSink[0] : eventSinkList;
    }

    public String deleteEventSink(String name){
        try {
            stub.deleteEventSink(name);
            return "Event Sink Successfully Deleted";
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Error Occured while deleting Event Sink";
        }
    }

    public void updateEventSink(String name, String username, String password, String receiverUrl,String authenticatorUrl){
        try {
            stub.updateEventSink(name,username,password,receiverUrl,authenticatorUrl);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
