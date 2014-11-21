/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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

	public void writeEventSinkXml(EventSink eventSink) {
		try {
			stub.writeEventSink(eventSink.getName(), eventSink.getUsername(), eventSink.getPassword(),
			                    eventSink.getReceiverUrl(), eventSink.getAuthenticatorUrl());
		} catch (RemoteException e) {
			log.error("Error occured while wring Event Sink");
		}
	}

	public org.wso2.carbon.event.sink.config.xsd.EventSink[] getAllEventSinks() {
		org.wso2.carbon.event.sink.config.xsd.EventSink[] eventSinkList =
				new org.wso2.carbon.event.sink.config.xsd.EventSink[0];
		try {
			eventSinkList = stub.getAllEventSinks();
		} catch (RemoteException e) {
			log.error("Error Occured while obtaining list of Event Sinks");
		}
		return eventSinkList == null ? new org.wso2.carbon.event.sink.config.xsd.EventSink[0] : eventSinkList;
	}

	public boolean deleteEventSink(String name) {
		try {
			if(stub.deleteEventSink(name)){
				System.out.print("deleted+++++++++++++++");
				return true;
			}else {
				System.out.print("not deleted+++++++++++++++");
			}

		} catch (RemoteException e) {
				log.error("Event Sink cannot be deleted");
		}
		return false;
	}

	public boolean updateEventSink(String name, String username, String password, String receiverUrl,
	                            String authenticatorUrl) {
		try {
			return stub.updateEventSink(name, username, password, receiverUrl, authenticatorUrl);
		} catch (RemoteException e) {
			log.error("Error occured while updating Event Sink");
		}
		return false;
	}

}
