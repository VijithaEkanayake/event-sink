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
package org.wso2.carbon.event.sink.config.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.core.AbstractAdmin;
import org.wso2.carbon.event.sink.config.EventSink;
import org.wso2.carbon.event.sink.config.EventSinkXmlWriter;
import org.wso2.carbon.event.sink.config.services.utils.CryptographyManager;

/**
 * Admin service class to expose all web services
 */
public class EventSinkConfigAdmin extends AbstractAdmin {
    private static final Log log = LogFactory.getLog(EventSinkConfigAdmin.class);
    private EventSinkXmlWriter registryManager;
    private CryptographyManager cryptographyManager;

    public EventSinkConfigAdmin() {
        registryManager = new EventSinkXmlWriter();
        cryptographyManager = new CryptographyManager();
    }

//    public boolean saveResourceString(String resourceString, String bamServerProfileLocation) {
//        registryManager.saveResourceString(resourceString, bamServerProfileLocation);
//        return true;
//    }
//
//    public String getResourceString(String bamServerProfileLocation){
//        return registryManager.getResourceString(bamServerProfileLocation);
//    }
//
//    public boolean resourceAlreadyExists(String bamServerProfileLocation){
//        return registryManager.resourceAlreadyExists(bamServerProfileLocation);
//    }
//
//    public boolean removeResource(String path){
//        return registryManager.removeResource(path);
//    }
//
//    public EventSink getBamServerConfig(String bamServerConfigLocation){
//        String resourceString = registryManager.getResourceString(bamServerConfigLocation);
//        EventSinkConfigBuilder bamServerConfigBuilder = new EventSinkConfigBuilder();
//        try {
//            OMElement resourceElement = new StAXOMBuilder(new ByteArrayInputStream(resourceString.getBytes(Charset.forName("UTF-8")))).getDocumentElement();
//            bamServerConfigBuilder.createEventSinkConfig(resourceElement);
//            return bamServerConfigBuilder.getEventSink();
//        } catch (XMLStreamException e) {
//            String errorMsg = "Failed to create XML OMElement from the String. " + e.getMessage();
//            log.error(errorMsg, e);
//        }
//        return null;
//    }


    public boolean saveEventSinkConfig(EventSink eventSinkConfig){
        return true; // TODO Implement
    }

//    public boolean bamServerConfigExists(String bamServerConfigLocation){
//        return registryManager.resourceAlreadyExists(bamServerConfigLocation);
//    }
//
//    public boolean addCollection(String bamServerProfileCollectionLocation){
//        return registryManager.addCollection(bamServerProfileCollectionLocation);
//    }
//
//    public String[] getServerProfileNameList(String bamServerProfileCollectionLocation){
//        return registryManager.getServerProfileNameList(bamServerProfileCollectionLocation);
//    }
    
    public String encryptAndBase64Encode(String plainText) {
        return cryptographyManager.encryptAndBase64Encode(plainText);
    }
    
    public String base64DecodeAndDecrypt(String cipherText) {
        return cryptographyManager.base64DecodeAndDecrypt(cipherText);
    }

}
