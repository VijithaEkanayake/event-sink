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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.event.sink.EventSinkException;
import org.wso2.carbon.event.sink.config.services.utils.CryptographyManager;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.carbon.utils.ServerConstants;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Does Event Sinks xml file reading
 */
public class EventSinkXmlReader {

    private static final Log log = LogFactory.getLog(EventSinkXmlReader.class);


    /**
     * Obtain corresponding tenant Event Sink deployment directory path
     * @return directorypath
     */
    public String getTenantDeployementDirectoryPath(){
        String filePath="";
        int tenantId = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        String tenantFilePath = CarbonUtils.getCarbonTenantsDirPath();
        if(tenantId>0 && !(tenantId==-1234)){
            filePath = tenantFilePath + File.separator + tenantId + File.separator + "event-sinks"  + File.separator;
        }else if(tenantId==-1234){
            String carbonHome = System.getProperty(ServerConstants.CARBON_HOME);
            filePath = carbonHome + File.separator + "repository" + File.separator + "deployment" + File.separator + "server" +  File.separator + "event-sinks" + File.separator;
        }

        return filePath;
    }

    /**
     * Obtain all the Event Sinks
     * @return EventSinks List
     */

    public List<EventSink> getAllEventSinks() {
        String filePath="";
        EventSink eventSink;
        EventSinkConfigBuilder eventSinkConfigBuilder = new EventSinkConfigBuilder();
        List<EventSink> eventSinkList = new ArrayList<EventSink>();
        filePath = this.getTenantDeployementDirectoryPath();
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File sink : directoryListing) {

                try {
                    if (FilenameUtils.getExtension(sink.getName()).equals("xml")) {
                        FileInputStream fileInputStream = new FileInputStream(sink);
                        eventSink = eventSinkConfigBuilder.createEventSinkConfig(this.toOM(fileInputStream), FilenameUtils.removeExtension(sink.getName()));
                        eventSink.setPassword(base64DecodeAndDecrypt(eventSink.getPassword()));
                        eventSinkList.add(eventSink);
                    }

                } catch (FileNotFoundException e) {
                    log.error("Failed to read file to load event sink. Error: " + e.getLocalizedMessage());
                } catch (EventSinkException e) {
                    log.error("Failed to read event sink from file. File: " + sink.getAbsolutePath() + ", Error: " + e.getLocalizedMessage());
                }
            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }

        return eventSinkList;
    }

    /**
     * Finds Event Sink with specific name
     *
     * @param name the Event Sink name to obtain
     * @return EventSink
     */

    public EventSink getEventSinkFromName(String name) {
        String filePath="";
        EventSinkConfigBuilder eventSinkConfigBuilder = new EventSinkConfigBuilder();
        EventSink eventSink = new EventSink();
        filePath = this.getTenantDeployementDirectoryPath();
        File eventSinkFile = new File(filePath + name + ".xml");

        // if the directory does not exist, create it
        if (eventSinkFile.exists()) {
            eventSink.setName(eventSinkFile.getName());
            try {
                FileInputStream fileInputStream = new FileInputStream(eventSinkFile);
                eventSink = eventSinkConfigBuilder.createEventSinkConfig(this.toOM(fileInputStream), eventSink.getName());
                eventSink.setPassword(base64DecodeAndDecrypt(eventSink.getPassword()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (EventSinkException e) {
                e.printStackTrace();
            }
        }
        return eventSink;
    }

    /**
     * Deletes Event Sink with Specific name
     *
     * @param name the Event Sink name to delete
     */

    public void deleteEventSinkFromName(String name) {
        String filePath="";
        filePath = this.getTenantDeployementDirectoryPath();
        File eventSinkFile = new File(filePath + name + ".xml");
        if (eventSinkFile.exists()) {
            try {
                eventSinkFile.delete();
            } catch (Exception e) {
                log.error("Error occured while deleting event-sink xml file");
            }
        }
    }

    /**
     * Converts an XML inputStream into an OMElement
     *
     * @param inputStream the XML inputStream to be converted
     * @return <code>OMElement</code> instance
     */
    public OMElement toOM(InputStream inputStream) throws EventSinkException {

        try {
            XMLStreamReader reader =
                    XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
            StAXOMBuilder builder = new StAXOMBuilder(reader);
            return builder.getDocumentElement();

        } catch (XMLStreamException e) {
            throw new EventSinkException("Error creating a OMElement from an input stream : ",
                    e);
        }
    }

    /**
     * Decode and decrypts a given encrypted and encoded string
     *
     * @param cipherText the String to be converted
     * @return Decoded and decrypted String
     */
    public String base64DecodeAndDecrypt(String cipherText) {
        CryptographyManager cryptographyManager = new CryptographyManager();
        return cryptographyManager.base64DecodeAndDecrypt(cipherText);
    }
}
