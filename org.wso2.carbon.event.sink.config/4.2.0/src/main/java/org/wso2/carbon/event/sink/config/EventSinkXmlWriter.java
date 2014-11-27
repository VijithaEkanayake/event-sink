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
package org.wso2.carbon.event.sink.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.wso2.carbon.event.sink.EventSink;
import org.wso2.carbon.event.sink.EventSinkException;
import org.wso2.carbon.event.sink.config.services.utils.CryptographyManager;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Creates Event Sink xml artifact and does operation on it
 */
public class EventSinkXmlWriter {
	private static final Log log = LogFactory.getLog(EventSinkXmlWriter.class);
//TODO: send a feedback to writing if an exception occurs(throw axis2 fault)


	/**
	 * Writes given Event Sink details to xml file
	 *
	 * @param eventSink the Event Sink to be write
	 */
	public boolean writeEventSink(EventSink eventSink) throws EventSinkException {
		String filePath = "";
		filePath = EventSinkXmlReader.getTenantDeployementDirectoryPath();
		this.createEventSinkDirectory(filePath);
		EventSinkConfigXml eventSinkConfigXml = new EventSinkConfigXml();
		BufferedWriter bufferedWriter=null;
		try {
			bufferedWriter =
					new BufferedWriter(new FileWriter(new File(filePath, eventSink.getName() + ".xml")));
			String unFormattedXml = eventSinkConfigXml
					.buildEventSink(eventSink.getUsername(), encryptAndBase64Encode(eventSink.getPassword()),
					                eventSink.getReceiverUrlSet(), eventSink.getAuthenticationUrlSet()).toString();

			///formatting xml
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(unFormattedXml));
			final Document document = db.parse(is);
			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(100);
			format.setIndenting(true);
			format.setIndent(4);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			bufferedWriter.write(out.toString());

		} catch (FileNotFoundException e) {
			log.error("Failed to open file to write event sink. File: " + filePath + ", error: " +
			          e);
		} catch (IOException e) {
			log.error("Failed to write event sink to file. File: " + filePath + ", error: " + e);
		} catch (ParserConfigurationException e) {
			log.error("Internal error occurred while writing event sink. Failed to format XML. error: " +
			          e);
		} catch (SAXException e) {
			log.error(
					"Internal error occurred while writing event sink. Invalid XML. error: " + e);
		}
		finally {
			if (bufferedWriter!=null){
				try {
					bufferedWriter.flush();
					bufferedWriter.close();
					return true;
				} catch (IOException e) {
					log.error("Failed to close stream, error: " +
					          e);
				}
			}
		}
		return false;
	}

	/**
	 * Updates given Event Sink details
	 *
	 * @param eventSink the Event Sink to be updated
	 */
	public boolean updateEventSink(EventSink eventSink) throws EventSinkException {
		String filePath = "";
		filePath = EventSinkXmlReader.getTenantDeployementDirectoryPath();
		File eventSinkFile = new File(filePath + eventSink.getName() + ".xml");
		if (eventSinkFile.exists()) {
			eventSinkFile.delete();
			writeEventSink(eventSink);
			return true;
		} else {
			log.error("Event Sink file cannot be found with name : " + eventSink.getName() + " in location " +
			          filePath);
		}
		return false;
	}

	/**
	 * Creates a directory in the specified location
	 *
	 * @param filePath location the directory should be created
	 */
	private void createEventSinkDirectory(String filePath) {
		File eventSinksDir = new File(filePath);

		// if the directory does not exist, create it
		if (!eventSinksDir.exists()) {
			try {
				eventSinksDir.mkdir();
			} catch (SecurityException e) {
				log.error("Couldn't create event-Sinks directory in following location"+filePath+" with ERROR : "+e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Encrypts and encodes a string
	 *
	 * @param plainText the String to be converted
	 * @return Encrypted and encoded String
	 */
	public String encryptAndBase64Encode(String plainText) {
		CryptographyManager cryptographyManager = new CryptographyManager();
		return cryptographyManager.encryptAndBase64Encode(plainText);
	}
}