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

package org.wso2.carbon.event.sink.internal;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.core.util.CryptoException;
import org.wso2.carbon.core.util.CryptoUtil;
import org.wso2.carbon.event.sink.EventSink;

import javax.xml.namespace.QName;
import java.nio.charset.Charset;

public class EventSinkImpl implements EventSink {
    private static final Log log = LogFactory.getLog(EventSinkImpl.class);
    static final QName RECEIVER_URL_Q = new QName("receiverUrl");
    static final QName AUTHENTICATOR_URL_Q = new QName("authenticatorUrl");
    static final QName USERNAME_Q = new QName("userName");
    static final QName PASSWORD_Q = new QName("password");

    private String name;
    private String receiverUrlSet;
    private String authenticationUrlSet;
    private String username;
    private String password;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getReceiverUrlSet() {
        return receiverUrlSet;
    }

    public void setReceiverUrlSet(String urlSet) {
        this.receiverUrlSet = urlSet;
    }

    @Override
    public String getAuthenticationUrlSet() {
        return authenticationUrlSet;
    }

    public void setAuthenticationUrlSet(String urlSet) {
        this.authenticationUrlSet = urlSet;
    }

    public static EventSink createEventSink(OMElement eventSinkElement) throws EventSinkException {

        EventSinkImpl eventSink = new EventSinkImpl();

        OMElement receiverUrl = eventSinkElement.getFirstChildWithName(RECEIVER_URL_Q);
        if (receiverUrl == null || "".equals(receiverUrl.getText())) {
            throw new EventSinkException(RECEIVER_URL_Q.getLocalPart() + " is missing in thrift endpoint config");
        }
        eventSink.setReceiverUrlSet(receiverUrl.getText());

        OMElement authenticatorUrl = eventSinkElement.getFirstChildWithName(AUTHENTICATOR_URL_Q);
        if (authenticatorUrl != null) {
            eventSink.setAuthenticationUrlSet(authenticatorUrl.getText());
        }

        OMElement userName = eventSinkElement.getFirstChildWithName(USERNAME_Q);
        if (userName == null || "".equals(userName.getText())) {
            throw new EventSinkException(USERNAME_Q.getLocalPart() + " is missing in thrift endpoint config");
        }
        eventSink.setUsername(userName.getText());

        OMElement password = eventSinkElement.getFirstChildWithName(PASSWORD_Q);
        if (password == null || "".equals(password.getText())) {
            throw new EventSinkException(PASSWORD_Q.getLocalPart() + " attribute missing in thrift endpoint config");
        }
        eventSink.setPassword(base64DecodeAndDecrypt(password.getText()));

        return eventSink;
    }

    private static String encryptAndBase64Encode(String plainText) {
        try {
            return CryptoUtil.getDefaultCryptoUtil().encryptAndBase64Encode(plainText.getBytes(Charset.forName("UTF-8")));
        } catch (CryptoException e) {
            String errorMsg = "Encryption and Base64 encoding error. " + e.getMessage();
            log.error(errorMsg, e);
        }
        return null;
    }

    private static String base64DecodeAndDecrypt(String cipherText) {
        try {
            return new String(CryptoUtil.getDefaultCryptoUtil().base64DecodeAndDecrypt(cipherText), Charset.forName("UTF-8"));
        } catch (Exception e) {
            String errorMsg = "Base64 decoding and decryption error. " + e.getMessage();
            log.error(errorMsg, e);
        }
        return null;
    }
}