
<%--
  ~  Copyright (c) 2008, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
  ~
  ~  Licensed under the Apache License, Version 2.0 (the "License");
  ~  you may not use this file except in compliance with the License.
  ~  You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~  Unless required by applicable law or agreed to in writing, software
  ~  distributed under the License is distributed on an "AS IS" BASIS,
  ~  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~  See the License for the specific language governing permissions and
  ~  limitations under the License.
  --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.jaxen.JaxenException" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.event.sink.config.ui.PublishEventMediatorConfigAdminClient" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.event.sink.config.EventSink" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
    ConfigurationContext configContext =
            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);

    PublishEventMediatorConfigAdminClient publishEventMediatorConfigAdminClient =
            new PublishEventMediatorConfigAdminClient(cookie, backendServerURL, configContext, request.getLocale());


    String propertyCountParameter = request.getParameter("propertyCount");
    if (propertyCountParameter != null && !"".equals(propertyCountParameter)) {
        int propertyCount = 0;
        try {
            propertyCount = Integer.parseInt(propertyCountParameter.trim());
            for (int i = 0; i <= propertyCount; i++) {
                EventSink eventSink = new EventSink();
                String name = request.getParameter("propertyName" + i);

                if (name != null && !"".equals(name)) {
                    eventSink.setName(name);
                    String valueId = "propertyUsername" + i;
                    String username = request.getParameter(valueId);
                    eventSink.setUsername(username);
                    String password = request.getParameter("propertyPassword" + i);
                    eventSink.setPassword(password);
                    String receiverUrl = request.getParameter("propertyReceiverUrl" + i);
                    eventSink.setReceiverUrl(receiverUrl);
                    String authenticatorUrl = request.getParameter("propertyAuthenticatorUrl" + i);
                    eventSink.setAuthenticatorUrl(authenticatorUrl);
                }
               publishEventMediatorConfigAdminClient.writeEventSinkXml(eventSink);
            }
        } catch (NumberFormatException ignored) {
            throw new RuntimeException("Invalid number format");
        }
    }


%>

