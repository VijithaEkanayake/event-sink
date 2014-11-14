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

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.deployment.Deployer;
import org.apache.axis2.deployment.DeploymentException;
import org.apache.axis2.deployment.repository.util.DeploymentFileData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSinkDeployer implements Deployer {

    private static final Log log = LogFactory.getLog(DataSinkDeployer.class);

    @Override
    public void init(ConfigurationContext configurationContext) {
    }

    @Override
    public void deploy(DeploymentFileData deploymentFileData) throws DeploymentException {
        log.error("heloooooooooo " + deploymentFileData.getAbsolutePath());
    }

    @Override
    public void setDirectory(String s) {
    }

    @Override
    public void setExtension(String s) {
    }

    @Override
    public void undeploy(String s) throws DeploymentException {
    }

    @Override
    public void cleanup() throws DeploymentException {
    }
}