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
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.event.sink.config.ui.PublishEventMediatorConfigAdminClient" %>
<%@ page import="org.wso2.carbon.event.sink.config.xsd.EventSink" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    response.setHeader("Cache-Control", "no-cache");
%>

<script type="text/javascript">

    function addproperty(name, nameemptymsg, valueemptymsg) {

        if (!isValidProperties(nameemptymsg, valueemptymsg)) {
            return false;
        }

        var propertyCount = document.getElementById("propertyCount");
        var i = propertyCount.value;

        var currentCount = parseInt(i);
        currentCount = currentCount + 1;

        propertyCount.value = currentCount;

        var propertytable = document.getElementById("propertytable");
        propertytable.style.display = "";
        var propertytbody = document.getElementById("propertytbody");

        var propertyRaw = document.createElement("tr");
        propertyRaw.setAttribute("id", "propertyRaw" + i);

        var nameTD = document.createElement("td");
        nameTD.innerHTML = "<input type='text' name='propertyName" + i + "' id='propertyName" + i + "'" +
                " />";
        var usernameTD = document.createElement("td");
        usernameTD.innerHTML = "<input type='text' name='propertyUsername" + i + "' id='propertyUsername" + i + "'" +
                " />";
        var passwordTD = document.createElement("td");
        passwordTD.innerHTML = "<input type='password' name='propertyPassword" + i + "' id='propertyPassword" + i + "'" +
                " />";
        var receiverUrlTD = document.createElement("td");
        receiverUrlTD.innerHTML = "<input type='text' name='propertyReceiverUrl" + i + "' id='propertyReceiverUrl" + i + "'" +
                " />";
        var authenticatorUrlTD = document.createElement("td");
        authenticatorUrlTD.innerHTML = "<input type='text' name='propertyAuthenticatorUrl" + i + "' id='propertyAuthenticatorUrl" + i + "'" +
                " />";
        var deleteTD = document.createElement("td");
        deleteTD.innerHTML = "<a href='#' class='delete-icon-link' onclick='deleteproperty(" + i + ");return false;'>" + ["Delete"] + "</a>";

        propertyRaw.appendChild(nameTD);
        propertyRaw.appendChild(usernameTD);
        propertyRaw.appendChild(passwordTD);
        propertyRaw.appendChild(receiverUrlTD);
        propertyRaw.appendChild(authenticatorUrlTD);
        propertyRaw.appendChild(deleteTD);
        propertytbody.appendChild(propertyRaw);
        return true;
    }

    function isValidProperties(nameemptymsg, valueemptymsg) {

        var nsCount = document.getElementById("propertyCount");
        var i = nsCount.value;

        var currentCount = parseInt(i);

        if (currentCount >= 1) {
            for (var k = 0; k < currentCount; k++) {
                var prefix = document.getElementById("propertyName" + k);
                if (prefix != null && prefix != undefined) {
                    if (prefix.value == "") {
                        CARBON.showWarningDialog(nameemptymsg)
                        return false;
                    }
                }
                var uri = document.getElementById("propertyValue" + k);
                if (uri != null && uri != undefined) {
                    if (uri.value == "") {
                        CARBON.showWarningDialog(valueemptymsg)
                        return false;
                    }
                }
            }
        }
        return true;
    }

    function deleteproperty(i) {
        var eventSinkName = document.getElementById("propertyName" + i).innerHTML.trim();

        CARBON.showConfirmationDialog("Are you sure, you want to delete event sink '" + eventSinkName + "'?", function () {
            jQuery.ajax({
                type: "GET",
                url: "../event-sink-config/update_event_sink_configuration.jsp",
                data: {action: "delete", name: eventSinkName},
                success: function (data) {
                    CARBON.showInfoDialog("Deleted");
                    var propRow = document.getElementById("propertyRaw" + i);
                    if (propRow != undefined && propRow != null) {
                        var parentTBody = propRow.parentNode;
                        if (parentTBody != undefined && parentTBody != null) {
                            parentTBody.removeChild(propRow);
                            if (!isContainRaw(parentTBody)) {
                                var propertyTable = document.getElementById("propertytable");
                                propertyTable.style.display = "none";
                            }
                        }
                    }
                }
            })
        });
    }

    function isContainRaw(tbody) {
        if (tbody.childNodes == null || tbody.childNodes.length == 0) {
            return false;
        } else {
            for (var i = 0; i < tbody.childNodes.length; i++) {
                var child = tbody.childNodes[i];
                if (child != undefined && child != null) {
                    if (child.nodeName == "tr" || child.nodeName == "TR") {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    function resetDisplayStyle(displayStyle) {
        document.getElementById('ns-edior-th').style.display = displayStyle;
        var nsCount = document.getElementById("propertyCount");
        var i = nsCount.value;

        var currentCount = parseInt(i);

        if (currentCount >= 1) {
            for (var k = 0; k < currentCount; k++) {
                var nsEditorButtonTD = document.getElementById("nsEditorButtonTD" + k);
                if (nsEditorButtonTD != undefined && nsEditorButtonTD != null) {
                    nsEditorButtonTD.style.display = displayStyle;
                }
            }
        }
    }

    function editEventSink(i) {
        var name = document.getElementById("propertyName" + i).innerHTML.trim();
        var username = document.getElementById("propertyUsername" + i).innerHTML.trim();
        var password = document.getElementById("password" + i).value;
        var receiverUrl = document.getElementById("propertyReceiverUrl" + i).innerHTML.trim();
        var authenticatorUrl = document.getElementById("propertyAuthenticatorUrl" + i).innerHTML.trim();

        var eventSinkParams = "name=" + name + "&"
                + "username=" + username + "&"
                + "password=" + password + "&"
                + "receiverUrl=" + receiverUrl + "&"
                + "authenticatorUrl=" + authenticatorUrl + "&"
                + "action=edit";
        window.location.href = "add_event_sink.jsp?" + eventSinkParams;

    }


</script>


<%
    String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
    ConfigurationContext configContext =
            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);

    PublishEventMediatorConfigAdminClient publishEventMediatorConfigAdminClient =
            new PublishEventMediatorConfigAdminClient(cookie, backendServerURL, configContext, request.getLocale());
    EventSink[] eventSinkList = publishEventMediatorConfigAdminClient.getAllEventSinks();
    //String propertyTableStyle = mediatorPropertyList.isEmpty() ? "display:none;" : "";

%>

<fmt:bundle basename="org.wso2.carbon.event.sink.config.ui.i18n.Resources">
    <carbon:jsi18n
            resourceBundle="org.wso2.carbon.event.sink.config.ui.i18n.JSResources"
            request="<%=request%>"
            i18nObjectName="logi18n"/>
    <div id="middle">

            <div id="workArea">

                <table class="normal" width="100%">
                    <tr>
                        <td>
                            <h2><fmt:message key="publishEvent.configuration.header"/></h2>
                        </td>
                    </tr>

                    <tr>
                        <td>


                            <div style="margin-top:0px;">

                                <table id="propertytable" class="styledInner">
                                    <thead>
                                    <tr>
                                        <th width="15%"><fmt:message
                                                key="publishEvent.configuration.attribute.name"/></th>
                                        <th width="10%"><fmt:message
                                                key="publishEvent.configuration.attribute.username"/></th>
                                        <th width="15%"><fmt:message
                                                key="publishEvent.configuration.attribute.receiverUrl"/></th>
                                        <th width="15%"><fmt:message
                                                key="publishEvent.configuration.attribute.authenticatorUrl"/></th>
                                        <th colspan="2" width="15%"><fmt:message key="publishEvent.configuration.action"/></th>
                                    </tr>
                                    <tbody id="propertytbody">
                                    <%
                                        int i = 0;
                                        for (EventSink eventSink : eventSinkList) {
                                    %>
                                    <tr id="propertyRaw<%=i%>">
                                        <td>
                                            <div name="propertyName<%=i%>" id="propertyName<%=i%>">
                                                <%=eventSink.getName()%>
                                            </div>
                                        </td>
                                        <td>
                                            <div name="propertyUsername<%=i%>" id="propertyUsername<%=i%>">
                                                <%=eventSink.getUsername()%>
                                            </div>
                                        </td>
                                        <td>
                                            <div name="propertyReceiverUrl<%=i%>" id="propertyReceiverUrl<%=i%>">
                                                <%=eventSink.getReceiverUrl()%>
                                            </div>
                                        </td>
                                        <td>
                                            <div name="propertyAuthenticatorUrl<%=i%>" id="propertyAuthenticatorUrl<%=i%>">
                                                <%=eventSink.getAuthenticatorUrl()%>
                                            </div>
                                        </td>
                                        <td><a href="#" class="edit-icon-link"
                                               onclick="editEventSink(<%=i%>);return false;">Edit</a></td>

                                        <td><a href="#" class="delete-icon-link"
                                               onclick="deleteproperty(<%=i%>);return false;"><fmt:message
                                                key="publishEvent.configuration.action.delete"/></a></td>

                                    </tr>
                                    <input type="hidden" name="propertyCount" id="propertyCount" value="<%=i%>"/>
                                    <input type="hidden" name="password" id="password<%=i%>" value="<%=eventSink.getPassword()%>"/>
                                    <%
                                            i++;
                                        }

                                    %>

                                    </tbody>
                                    </thead>
                                </table>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div style="margin-top:10px;">
                                <a class="add-icon-link"
                                   href="add_event_sink.jsp?action=add">Add Event Sink</a>
                            </div>
                        </td>
                    </tr>

                </table>
            </div>


    </div>

</fmt:bundle>