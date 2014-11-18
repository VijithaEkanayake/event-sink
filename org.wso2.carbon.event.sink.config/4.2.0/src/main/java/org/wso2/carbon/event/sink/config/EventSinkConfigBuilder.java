/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.event.sink.config;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.synapse.SynapseException;
import org.apache.synapse.config.xml.XMLConfigConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder of EventSinkConfig from OMElements from the string fetched from EventSink xml file
 */
public class EventSinkConfigBuilder {

    public static final QName RECEIVER_URL_Q = new QName(XMLConfigConstants.SYNAPSE_NAMESPACE, "receiverUrl");
    public static final QName AUTHENTICATOR_URL_Q = new QName(XMLConfigConstants.SYNAPSE_NAMESPACE, "authenticatorUrl");
    public static final QName USERNAME_Q = new QName(XMLConfigConstants.SYNAPSE_NAMESPACE, "username");
    public static final QName PASSWORD_Q = new QName(XMLConfigConstants.SYNAPSE_NAMESPACE, "password");

    List<EventSink> eventSinksList = new ArrayList<EventSink>();

    private EventSink eventSink = new EventSink();

    public EventSink createEventSinkConfig(OMElement eventSinkConfigElement,String name){

        eventSink.setName(name);

        OMElement receiverUrl = eventSinkConfigElement.getFirstChildWithName(RECEIVER_URL_Q);
        if (receiverUrl == null) {
            throw new SynapseException(RECEIVER_URL_Q.getLocalPart() +" element missing");
        }
        eventSink.setReceiverUrl(receiverUrl.getText());

        OMElement authenticatorUrl = eventSinkConfigElement.getFirstChildWithName(AUTHENTICATOR_URL_Q);
        if (authenticatorUrl == null) {
            throw new SynapseException(AUTHENTICATOR_URL_Q.getLocalPart() +" element missing");
        }
        eventSink.setAuthenticatorUrl(authenticatorUrl.getText());

        OMElement username = eventSinkConfigElement.getFirstChildWithName(USERNAME_Q);
        if (username == null) {
            throw new SynapseException(USERNAME_Q.getLocalPart() +" element missing");
        }
        eventSink.setUsername(username.getText());

        OMElement password = eventSinkConfigElement.getFirstChildWithName(PASSWORD_Q);
        if (password == null) {
            throw new SynapseException(PASSWORD_Q.getLocalPart() +" element missing");
        }
        eventSink.setPassword(password.getText());
        return eventSink;
    }



    private boolean isNotNullOrEmpty(OMAttribute omAttribute){
        return omAttribute != null && !omAttribute.getAttributeValue().equals("");
    }

    public EventSink getEventSink(){
        return this.eventSink;
    }

}
