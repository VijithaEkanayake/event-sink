package org.wso2.carbon.event.sink.internal;

import org.wso2.carbon.event.sink.EventSink;
import org.wso2.carbon.event.sink.EventSinkService;

import java.util.List;

public class EventSinkServiceImpl implements EventSinkService {
    @Override
    public List<EventSink> getEventSinks() {
        return EventSinkStore.getInstance().getEventSinkList();
    }

    @Override
    public EventSink getEventSink(String eventSinkName) {
        return EventSinkStore.getInstance().getEventSink(eventSinkName);
    }
}