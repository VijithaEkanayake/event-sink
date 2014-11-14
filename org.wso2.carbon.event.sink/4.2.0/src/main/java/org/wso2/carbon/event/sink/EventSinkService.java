package org.wso2.carbon.event.sink;

import java.util.List;

public interface EventSinkService {
    public List<EventSink> getEventSinks();
    public EventSink getEventSink(String eventSinkName);
}