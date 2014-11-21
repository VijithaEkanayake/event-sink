/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.event.sink.config.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.core.AbstractAdmin;
import org.wso2.carbon.event.sink.config.EventSink;
import org.wso2.carbon.event.sink.config.EventSinkXmlReader;
import org.wso2.carbon.event.sink.config.EventSinkXmlWriter;

import java.util.List;

/**
 * Admin service class to expose all web services
 */
public class EventSinkConfigAdmin extends AbstractAdmin {
	private static final Log log = LogFactory.getLog(EventSinkConfigAdmin.class);

	public void writeEventSink(String name, String username, String password, String receiverUrl,
	                              String authenticatorUrl) {
		new EventSinkXmlWriter().writeEventSink(new EventSink(name, username, password, receiverUrl, authenticatorUrl));
	}

	public List<EventSink> getAllEventSinks() {
		return new EventSinkXmlReader().getAllEventSinks();
	}

	public EventSink getEventSinkFromName(String name) {
		return new EventSinkXmlReader().getEventSinkFromName(name);
	}

	public boolean deleteEventSink(String name) {
		return new EventSinkXmlReader().deleteEventSinkFromName(name);
	}

	public boolean updateEventSink(String name, String username, String password, String receiverUrl,
	                               String authenticatorUrl) {
		return new EventSinkXmlWriter().updateEventSink(new EventSink(name, username, password, receiverUrl, authenticatorUrl));
	}
}