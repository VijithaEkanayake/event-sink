package org.wso2.carbon.event.sink;

import org.wso2.carbon.databridge.agent.thrift.lb.LoadBalancingDataPublisher;

public interface EventSink {
    public String getName();
    public String getUsername();
    public String getPassword();
    public String getReceiverUrlSet();
    public String getAuthenticationUrlSet();
    public LoadBalancingDataPublisher getLoadBalancingDataPublisher();
}