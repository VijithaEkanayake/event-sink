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
            CARBON.showErrorDialog(eventSinki18n["specify.EventSinkName"]);
            return false;
        }
        var username = document.getElementById('propertyUsername0');
        if (username && username.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.Username"]);
            return false;
        }

        var password = document.getElementById('propertyPassword0');
        if (password && password.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.Password"]);
            return false;
        }
        var receiverUrl = document.getElementById('propertyReceiverUrl0');
        if (receiverUrl && receiverUrl.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.ReceiverUrl"]);
            return false;
        }
        var authenticatorUrl = document.getElementById('propertyAuthenticatorUrl0');
        if (authenticatorUrl && authenticatorUrl.value == "") {
            CARBON.showErrorDialog(eventSinki18n["specify.AuthenticatorUrl"]);
            return false;
        }
        return true;
    }
    function configureEventSink() {

        if (eventSinkValidate()) {

            var username = document.getElementById("propertyUsername0").value;
            var password = document.getElementById("propertyPassword0").value;
            var receiverUrl = document.getElementById("propertyReceiverUrl0").value;
            var authenticatorUrl = document.getElementById("propertyAuthenticatorUrl0").value;
            var propertyCount = document.getElementById("propertyCount").value;
            var action = document.getElementById("action").value;

            if (action == "add") {
                var name = document.getElementById("propertyName0").value;
                CARBON.showConfirmationDialog("Are you sure, you want to add event sink '" + name + "'?", function () {
                jQuery.ajax({
                    type: "GET",
                    url: "../event-sink-config/update_event_sink_configuration.jsp",
                    data: {action: "add", propertyName0: name, propertyCount: propertyCount, propertyUsername0: username, propertyPassword0: password, propertyReceiverUrl0: receiverUrl, propertyAuthenticatorUrl0: authenticatorUrl},
                    success: function (data) {
                        window.location.href = "event_sinks_configuration.jsp";
                    }
                });
                });
            } else if (action == "edit") {
                var name = document.getElementById("propertyName0").innerHTML.trim();
                CARBON.showConfirmationDialog("Are you sure, you want to update event sink '" + name + "'?", function () {
                jQuery.ajax({
                    type: "GET",
                    url: "../event-sink-config/update_event_sink_configuration.jsp",
                    data: {action: "edit", propertyName0: name, propertyCount: propertyCount, propertyUsername0: username, propertyPassword0: password, propertyReceiverUrl0: receiverUrl, propertyAuthenticatorUrl0: authenticatorUrl},
                    success: function (data) {
                        window.location.href = "event_sinks_configuration.jsp";
                    }
                });
             });
            }

        }
    }

</script>

<%
    response.setHeader("Cache-Control", "no-cache");
    String action = request.getParameter("action");
    String name = "";
    String username = "";
    String password = "";
    String receiverUrl = "";
    String authenticatorUrl = "";
    if (action.equals("edit")) {
        name = request.getParameter("name");
        username = request.getParameter("username");
        password = request.getParameter("password");
        receiverUrl = request.getParameter("receiverUrl");
        authenticatorUrl = request.getParameter("authenticatorUrl");
    }

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

        <div id="workArea">

            <table class="normal" width="100%">
                <tr>
                    <td>
                        <h2>
                            <%
                                if (action.equals("add")) {
                                    out.print("Add Event Sink");
                                } else {
                                    out.print("Edit Event Sink");
                                }
                            %>
                        </h2>
                    </td>
                </tr>

                <tr>
                    <td>


                        <div style="margin-top:0px;">

                            <table id="propertytable" class="styledInner">
                                <tbody id="propertytbody">
                                <%
                                    int i = 0;

                                %>
                                <tr>
                                    <td width="15%"><fmt:message key="publishEvent.configuration.attribute.name"/></td>
                                    <td>
                                        <%
                                            if (!action.equals("edit")) {
                                        %>

                                        <input type="text" name="propertyName0" id="propertyName0"
                                               class="esb-edit small_textbox"
                                               value="<%=name%>"/>
                                        <%
                                            } else {
                                        %>
                                        <div id="propertyName0"><%=name%></div>
                                        <%
                                            }
                                        %>

                                    </td>
                                </tr>
                                <tr>
                                    <td width="15%"><fmt:message key="publishEvent.configuration.attribute.username"/></td>
                                    <td><input type="text" name="propertyUsername0" id="propertyUsername0"
                                               class="esb-edit small_textbox"
                                               value="<%=username%>"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="15%"><fmt:message key="publishEvent.configuration.attribute.password"/></td>
                                    <td><input type="password" name="propertyPassword0" id="propertyPassword0"
                                               class="esb-edit small_textbox"
                                               value="<%=password%>"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="15%"><fmt:message key="publishEvent.configuration.attribute.receiverUrl"/></td>
                                    <td><input style="width: 98%;" type="text" name="propertyReceiverUrl0"
                                               id="propertyReceiverUrl0"

                                               value="<%=receiverUrl%>"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="15%"><fmt:message
                                            key="publishEvent.configuration.attribute.authenticatorUrl"/></td>
                                    <td><input style="width: 98%;" type="text" name="propertyAuthenticatorUrl0"
                                               id="propertyAuthenticatorUrl0"

                                               value="<%=authenticatorUrl%>"/>
                                    </td>
                                </tr>
                                <%

                                %>
                                <input type="hidden" name="propertyCount" id="propertyCount" value="0"/>
                                <input type="hidden" name="action" id="action" value="<%=action%>"/>

                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <tr>
            <td>
                <div style="margin-top:10px;">
                                    <span><a onClick='javaScript:configureEventSink();' style='background-image:
                                        url(images/save-button.gif);' class='icon-link addIcon'>Save</a></span>

                </div>
            </td>
        </tr>
    </div>
</fmt:bundle>