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

package org.wso2.carbon.event.sink.config.services.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.core.util.CryptoException;
import org.wso2.carbon.core.util.CryptoUtil;

import java.nio.charset.Charset;

/**
 * Encrypts and decrypts passwords. These operations are Base64 encoded to assure printable characters
 */
public class CryptographyManager {
//ToDO make it static  and throw exception
	private static final Log log = LogFactory.getLog(CryptographyManager.class);
	private CryptoUtil cryptoUtil;

	public CryptographyManager() {
		cryptoUtil = CryptoUtil.getDefaultCryptoUtil();
	}

	/**
	 * Encrypts and encodes a given plainText to a cipher text
	 *
	 * @param plainText
	 * @return Encrypted and encoded String
	 */
	public String encryptAndBase64Encode(String plainText) {
		try {
			return cryptoUtil.encryptAndBase64Encode(plainText.getBytes(Charset.forName("UTF-8")));
		} catch (CryptoException e) {
			String errorMsg = "Encryption and Base64 encoding error. " + e.getMessage();
			log.error(errorMsg, e);
		}
		return null;
	}

	/**
	 * Decode and decrypts a given cipher text to plain text
	 *
	 * @param cipherText
	 * @return Plain Text as a String
	 */
	public String base64DecodeAndDecrypt(String cipherText) {
		try {
			return new String(cryptoUtil.base64DecodeAndDecrypt(cipherText), Charset.forName("UTF-8"));
		} catch (CryptoException e) {
			String errorMsg = "Base64 decoding and decryption error. " + e.getMessage();
			log.error(errorMsg, e);
		}
		return null;
	}
}
