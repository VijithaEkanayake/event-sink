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

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.synapse.SynapseConstants;

/**
 * Creates the XML string to be stored in the Registry
 */
public class EventSinkConfigXml {

    private org.apache.axiom.om.OMFactory fac = OMAbstractFactory.getOMFactory();
    private org.apache.axiom.om.OMNamespace synNS = SynapseConstants.SYNAPSE_OMNAMESPACE;

    public OMElement buildServerProfile(String urlSet, String ip, String authenticationPort, String receiverPort,
                                        String userName, String password, String secure, String loadbalancer){
        OMElement serverProfileElement = this.serializeServerProfile();
        serverProfileElement.addChild(this.serializeConnection(loadbalancer, secure, urlSet, ip, authenticationPort, receiverPort));
        serverProfileElement.addChild(this.serializeCredential(userName, password));
        return serverProfileElement;
    }

    private OMElement serializeConnection(String loadbalancer, String secure, String urlSet, String ip,
                                          String authenticationPort, String receiverPort){
        OMElement credentialElement = fac.createOMElement("connection", synNS);
        credentialElement.addAttribute("loadbalancer", loadbalancer, null);
        credentialElement.addAttribute("secure", secure, null);
        credentialElement.addAttribute("urlSet", urlSet, null);
        credentialElement.addAttribute("ip", ip, null);
        credentialElement.addAttribute("authPort", authenticationPort, null);
        credentialElement.addAttribute("receiverPort", receiverPort, null);
        return credentialElement;
    }

    private OMElement serializeCredential(String userName, String password){
        OMElement credentialElement = fac.createOMElement("credential", synNS);
        credentialElement.addAttribute("userName", userName, null);
        credentialElement.addAttribute("password", password, null);
        return credentialElement;
    }

    private OMElement serializeServerProfile(){
        return fac.createOMElement("serverProfile", synNS);
    }

//    private OMElement serializePayload(){
//        return fac.createOMElement("payload", synNS);
//    }
//
//    private OMElement serializeEntry(String name, String value, String type){
//        OMElement entryElement = fac.createOMElement("entry", synNS);
//        entryElement.addAttribute("name", name, null);
//        entryElement.addAttribute("value", value, null);
//        entryElement.addAttribute("type", type, null);
//        return entryElement;
//    }
//
//    private OMElement serializeProperties(){
//        return fac.createOMElement("properties", synNS);
//    }
//
//    private OMElement serializeProperty(String name, String value, String type, boolean isExpression){
//        OMElement propertyElement = fac.createOMElement("property", synNS);
//        propertyElement.addAttribute("name", name, null);
//        propertyElement.addAttribute("value", value, null);
//        propertyElement.addAttribute("type", type, null);
//        if(isExpression){
//            propertyElement.addAttribute("isExpression", "true", null);
//        } else {
//            propertyElement.addAttribute("isExpression", "false", null);
//        }
//        return propertyElement;
//    }

}
