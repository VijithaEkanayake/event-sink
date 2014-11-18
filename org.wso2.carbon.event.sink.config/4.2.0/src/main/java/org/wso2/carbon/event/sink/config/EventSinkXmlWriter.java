package org.wso2.carbon.event.sink.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.wso2.carbon.event.sink.config.services.utils.CryptographyManager;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.carbon.utils.ServerConstants;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by vijithae on 11/17/14.
 */
public class EventSinkXmlWriter {
    private static final Log log = LogFactory.getLog(EventSinkXmlWriter.class);
    String carbonHome = System.getProperty(ServerConstants.CARBON_HOME);
    String filePath = carbonHome + File.separator + "repository" + File.separator + "deployment" + File.separator + "server" +  File.separator + "event-sinks";
    String tenantFilePath = CarbonUtils.getCarbonTenantsDirPath();
    EventSinkConfigXml eventSinkConfigXml = new EventSinkConfigXml();
    CryptographyManager cryptographyManager = new CryptographyManager();

    public void writeEventSink(EventSink eventSink) {
        this.createEventSinkDirectory(filePath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath, eventSink.getName() + ".xml")));
            String unFormattedXml = eventSinkConfigXml.buildEventSink(eventSink.getUsername(), encryptAndBase64Encode(eventSink.getPassword()), eventSink.getReceiverUrl(), eventSink.getAuthenticatorUrl()).toString();

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
            ///

            bufferedWriter.write(out.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            log.error("Failed to open file to write event sink. File: " + filePath + ", error: " + e.getLocalizedMessage());
        } catch (IOException e) {
            log.error("Failed to write event sink to file. File: " + filePath + ", error: " + e.getLocalizedMessage());
        } catch (ParserConfigurationException e) {
            log.error("Internal error occurred while writing event sink. Failed to format XML. error: " + e.getLocalizedMessage());
        } catch (SAXException e) {
            log.error("Internal error occurred while writing event sink. Invalid XML. error: " + e.getLocalizedMessage());
        }
    }

    private void createEventSinkDirectory(String filePath)
    {
        File eventSinksDir = new File(filePath);

        // if the directory does not exist, create it
        if (!eventSinksDir.exists())
        {
            try{
                eventSinksDir.mkdir();
            }catch (Exception e){
                log.error("Error occured while creating event-sinks file");
            }
        }
    }


    public ArrayList<String> getEventSinkNames() {


        ArrayList<String> eventSinkList = null;
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File eventSink : directoryListing) {
                eventSinkList.add(eventSink.getName());
            }
        }

        return eventSinkList;
    }

    public String encryptAndBase64Encode(String plainText) {
        return cryptographyManager.encryptAndBase64Encode(plainText);
    }

    public String base64DecodeAndDecrypt(String cipherText) {
        return cryptographyManager.base64DecodeAndDecrypt(cipherText);
    }
}
