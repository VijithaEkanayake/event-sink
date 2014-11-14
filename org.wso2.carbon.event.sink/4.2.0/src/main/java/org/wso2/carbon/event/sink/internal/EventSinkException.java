package org.wso2.carbon.event.sink.internal;

public class EventSinkException extends Exception {

    public EventSinkException(String s) {
        super(s);
    }

    public EventSinkException(String s, Throwable e) {
        super(s, e);
    }
}