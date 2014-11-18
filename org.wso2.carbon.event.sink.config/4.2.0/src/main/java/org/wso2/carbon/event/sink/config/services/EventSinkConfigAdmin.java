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

//import org.wso2.carbon.event.sink.config.EventSinkXmlWriter;

/**
 * Admin service class to expose all web services
 */
public class EventSinkConfigAdmin extends AbstractAdmin {
    private static final Log log = LogFactory.getLog(EventSinkConfigAdmin.class);
    private CryptographyManager cryptographyManager;

    public EventSinkConfigAdmin() {
        cryptographyManager = new CryptographyManager();
    }


    public boolean writeEventSink(String name, String username, String password, String receiverUrl,String authenticatorUrl){
        EventSink eventSink = new EventSink(name,username,password,receiverUrl,authenticatorUrl);
        EventSinkXmlWriter eventSinkXmlWriter = new EventSinkXmlWriter();
        eventSinkXmlWriter.writeEventSink(eventSink);
        return true;
    }









    public boolean saveEventSinkConfig(EventSink eventSinkConfig){
        return true; // TODO Implement
    }
    


}
