<!--
~ Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
~
~ WSO2 Inc. licenses this file to you under the Apache License,
~ Version 2.0 (the "License"); you may not use this file except
~ in compliance with the License.
~ You may obtain a copy of the License at
~
~ http://www.apache.org/licenses/LICENSE-2.0
~
~ Unless required by applicable law or agreed to in writing,
~ software distributed under the License is distributed on an
~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
~ KIND, either express or implied. See the License for the
~ specific language governing permissions and limitations
~ under the License.
-->

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    function eventSinkValidate() {
        var name = document.getElementById('propertyName0');
        if (name && name.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.StreamName"]);
            return false;
        }
        var username = document.getElementById('propertyUsername0');
        if (username && username.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.StreamVersion"]);
            return false;
        }

        var password = document.getElementById('propertyPassword0');
        if (password && password.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.StreamVersion"]);
            return false;
        }
        var receiverUrl = document.getElementById('propertyReceiverUrl0');
        if (receiverUrl && receiverUrl.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.StreamVersion"]);
            return false;
        }
        var authenticatorUrl = document.getElementById('propertyAuthenticatorUrl0');
        if (authenticatorUrl && authenticatorUrl.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.StreamVersion"]);
            return false;
        }

        return true;
    }
</script>

<%
    response.setHeader("Cache-Control", "no-cache");
    String action = request.getParameter("action");
    String name = request.getParameter("name");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String receiverUrl = request.getParameter("receiverUrl");
    String authenticatorUrl = request.getParameter("authenticatorUrl");
%>


<%
    //String propertyTableStyle = mediatorPropertyList.isEmpty() ? "display:none;" : "";

%>

<fmt:bundle basename="org.wso2.carbon.event.sink.config.ui.i18n.Resources">
    <carbon:jsi18n
            resourceBundle="org.wso2.carbon.event.sink.config.ui.i18n.JSResources"
            request="<%=request%>"
            i18nObjectName="eventSinki18n"/>
    <div id="middle">
        <form action="update_event_sink_configuration.jsp" method="post" onsubmit="return eventSinkValidate()">
            <div id="workArea">

                <table class="normal" width="100%">
                    <tr>
                        <td>
                            <h2><fmt:message key="publishEvent.configuration.header"/></h2>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <h3 class="mediator">
                                <fmt:message key="publishEvent.configuration.attributes"/></h3>

                            <div style="margin-top:0px;">

                                <table id="propertytable" class="styledInner">
                                    <thead>
                                    <tr>
                                        <th width="10%"><fmt:message
                                                key="publishEvent.configuration.attribute.name"/></th>
                                        <th width="50%"><fmt:message
                                                key="publishEvent.configuration.attribute.value"/></th>
                                    </tr>
                                    <tbody id="propertytbody">
                                    <%
                                        int i = 0;

                                    %>
                                    <tr>
                                        <td><fmt:message key="publishEvent.configuration.attribute.name"/></td>
                                        <td><input type="text" name="propertyName0" id="propertyName0"
                                                   class="esb-edit small_textbox"
                                                   value="<%=name%>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="publishEvent.configuration.attribute.username"/></td>
                                        <td><input type="text" name="propertyUsername0" id="propertyUsername0"
                                                   class="esb-edit small_textbox"
                                                   value="<%=username%>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="publishEvent.configuration.attribute.password"/></td>
                                        <td><input type="password" name="propertyPassword0" id="propertyPassword0"
                                                   class="esb-edit small_textbox"
                                                   value="<%=password%>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="publishEvent.configuration.attribute.receiverUrl"/></td>
                                        <td><input type="text" name="propertyReceiverUrl0" id="propertyReceiverUrl0"

                                                   value="<%=receiverUrl%>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message
                                                key="publishEvent.configuration.attribute.authenticatorUrl"/></td>
                                        <td><input type="text" name="propertyAuthenticatorUrl0"
                                                   id="propertyAuthenticatorUrl0"

                                                   value="<%=authenticatorUrl%>"/>
                                        </td>
                                    </tr>
                                    <%

                                    %>
                                    <input type="hidden" name="propertyCount" id="propertyCount" value="0"/>
                                    <input type="hidden" name="action" id="action" value="<%=action%>"/>

                                    </tbody>
                                    </thead>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <tr>
                <td>
                    <input type="submit" value="Save" class="button"/>
                </td>
            </tr>
            `

        </form>
    </div>
</fmt:bundle>