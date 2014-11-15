package org.wso2.carbon.event.sink.internal;

import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.event.sink.EventSink;

import java.util.*;

public class EventSinkStore {
    private static EventSinkStore instance = new EventSinkStore();
    private Map<String, EventSink> eventSinkMap = new HashMap<String, EventSink>(); //tenant id|sink name -> sink

    public static EventSinkStore getInstance() {
        return instance;
    }

    private EventSinkStore() {
    }

    public void addEventSink(EventSink eventSink) {
        String key = PrivilegedCarbonContext.getCurrentContext().getTenantId() + "|" + eventSink.getName();
        System.out.println("event sink " + eventSink.getName() + " added with key " + key);
        eventSinkMap.put(key, eventSink);
    }

    public void removeEventSink(String eventSinkName) {
        String key = PrivilegedCarbonContext.getCurrentContext().getTenantId() + "|" + eventSinkName;
        eventSinkMap.remove(key);
    }

    public EventSink getEventSink(String name) {
        String key = PrivilegedCarbonContext.getCurrentContext().getTenantId() + "|" + name;
        return eventSinkMap.get(key);
    }

    public List<EventSink> getEventSinkList() {
        String tenantKey = PrivilegedCarbonContext.getCurrentContext().getTenantId() + "|";
        List<EventSink> list = new ArrayList<EventSink>();
        for (Map.Entry<String, EventSink> entry : eventSinkMap.entrySet()) {
            if (entry.getKey().startsWith(tenantKey)) {
                list.add(entry.getValue());
            }
        }
        return list;
    }
}