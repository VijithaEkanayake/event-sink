/*
 * Copyright (c) ${YEAR}, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.event.sink;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.deployment.AbstractDeployer;
import org.apache.axis2.deployment.DeploymentException;
import org.apache.axis2.deployment.repository.util.DeploymentFileData;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.sink.internal.EventSinkImpl;
import org.wso2.carbon.event.sink.internal.EventSinkStore;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EventSinkDeployer extends AbstractDeployer {

    private static final Log log = LogFactory.getLog(EventSinkDeployer.class);

    @Override
    public void init(ConfigurationContext configurationContext) {
    }

    @Override
    public void deploy(DeploymentFileData deploymentFileData) throws DeploymentException {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(deploymentFileData.getAbsolutePath())));
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
            StAXOMBuilder builder = new StAXOMBuilder(reader);
            OMElement eventSink = builder.getDocumentElement();
            eventSink.build();
            String eventSinkName = FilenameUtils.getBaseName(deploymentFileData.getFile().getName());
            EventSinkStore.getInstance().addEventSink(EventSinkImpl.createEventSink(eventSink, eventSinkName));
            log.info("Deploying event sink: " + eventSinkName + " - file: " + deploymentFileData.getAbsolutePath());
        } catch (FileNotFoundException e) {
            throw new DeploymentException("Deployment artifact file \"" + deploymentFileData.getAbsolutePath() + "\" not found", e);
        } catch (XMLStreamException e) {
            throw new DeploymentException("Event sink XML in \"" + deploymentFileData.getAbsolutePath() + "\" is malformed", e);
        } catch (EventSinkException e) {
            throw new DeploymentException("Event sink configuration in \"" + deploymentFileData.getAbsolutePath() + "\" is invalid", e);
        }
    }

    @Override
    public void setDirectory(String s) {
    }

    @Override
    public void setExtension(String s) {
    }

    @Override
    public void undeploy(String fileName) throws DeploymentException {
        String eventSinkName = FilenameUtils.getBaseName(fileName);
        EventSinkStore.getInstance().removeEventSink(eventSinkName);
        log.info("Event sink named '" + eventSinkName + "' has been undeployed");
    }

    @Override
    public void cleanup() throws DeploymentException {
    }
}