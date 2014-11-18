package org.wso2.carbon.event.sink.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.formatter.core.exception.EventFormatterConfigurationException;
import org.wso2.carbon.event.sink.config.services.utils.CryptographyManager;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.carbon.utils.ServerConstants;
import org.wso2.carbon.event.formatter.core.internal.util.helper.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by vijithae on 11/17/14.
 */
public class EventSinkXmlWriter {
    private static final Log log = LogFactory.getLog(EventSinkXmlWriter.class);

    String carbonHome = System.getProperty(ServerConstants.CARBON_HOME);
    String filePath = carbonHome + File.separator + "repository" + File.separator + "conf" + File.separator;
    String tenantFilePath = CarbonUtils.getCarbonTenantsDirPath();
    EventSinkConfigXml eventSinkConfigXml = new EventSinkConfigXml();
    CryptographyManager cryptographyManager = new CryptographyManager();

    public void writeEventSink(EventSink eventSink ){

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath,eventSink.getName()+".xml")));
            String unformatted = new XmlFormatter().format(eventSinkConfigXml.buildEventSink(eventSink.getUsername(),encryptAndBase64Encode(eventSink.getPassword()),eventSink.getReceiverUrl(),eventSink.getAuthenticatorUrl()).toString());
            bufferedWriter.write(unformatted);

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EventFormatterConfigurationException e) {
            e.printStackTrace();
        }

    }






    public ArrayList<String> getEventSinkNames(){


        ArrayList<String> eventSinkList=null;
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null){
            for(File eventSink : directoryListing){
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
