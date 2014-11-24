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

package org.wso2.carbon.event.sink.config;

/**
 * EventSink
 */
public class EventSink {

	private String name;
	private String username;
	private String password;
	private String receiverUrl;
	private String authenticatorUrl;

	public EventSink() {
	}

	public EventSink(String name, String username, String password, String receiverUrl, String authenticatorUrl) {
		this.setName(name);
		this.setUsername(username);
		this.setPassword(password);
		this.setReceiverUrl(receiverUrl);
		this.setAuthenticatorUrl(authenticatorUrl);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReceiverUrl() {
		return receiverUrl;
	}

	public void setReceiverUrl(String receiverUrl) {
		this.receiverUrl = receiverUrl;
	}

	public String getAuthenticatorUrl() {
		return authenticatorUrl;
	}

	public void setAuthenticatorUrl(String authenticatorUrl) {
		this.authenticatorUrl = authenticatorUrl;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}