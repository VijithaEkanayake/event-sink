package org.wso2.carbon.event.sink;

public interface EventSink {
    public String getName();
    public String getUsername();
    public String getPassword();
    public String getReceiverUrlSet();
    public String getAuthenticationUrlSet();
}