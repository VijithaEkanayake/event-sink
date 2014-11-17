/*
 * Copyright WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.event.sink.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.event.sink.EventSinkService;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

/**
 *
 * @scr.component name="org.wso2.carbon.event.sink.EventSinkServiceComponent" immediate="true"
 */
public class EventSinkServiceComponent {
    private static Log log = LogFactory.getLog(EventSinkServiceComponent.class);
    private ServiceRegistration serviceRegistration;

    protected void activate(final ComponentContext componentContext) {
        final BundleContext bundleContext = componentContext.getBundleContext();
        serviceRegistration = bundleContext.
                registerService(EventSinkService.class.getName(), new EventSinkServiceImpl(), null);

        if (log.isDebugEnabled()) {
            log.debug("Started EventSinkService");
        }
    }

    protected void deactivate(ComponentContext componentContext) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        if (log.isDebugEnabled()) {
            log.debug("Stopped EventSinkService");
        }
    }
}