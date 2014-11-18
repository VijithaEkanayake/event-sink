/**
 * Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.event.sink.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.utils.ServerConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Does Configuration Registry operations required to store/fetch BAM server configurations
 */
public class EventSinkXmlReader {

    private static final Log log = LogFactory.getLog(EventSinkXmlReader.class);

    String carbonHome = System.getProperty(ServerConstants.CARBON_HOME);
    String filePath = carbonHome + File.separator + "repository" + File.separator + "deployemnt" + File.separator + "server" + File.separator + "deployemnt" + File.separator + "event-sinks" + File.separator;

    public List<EventSink> getAllEventSinks(){
        EventSink eventSink = new EventSink();
        List<EventSink> eventSinkList = new ArrayList<EventSink>();

        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File sink : directoryListing) {


            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }

        return  eventSinkList;
    }



}
