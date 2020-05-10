package com.thirteen.java;

import java.util.ResourceBundle;

/**
 * Author: thirteen
 * date-time: 2020-04-23 19:13
 **/
public class Configuration {
    private String AgentHost;
    private int AgentPort;
    public Configuration(){
        ResourceBundle property = ResourceBundle.getBundle("com.thirteen.resource.property");
        setAgentHost(property.getString("AgentHost"));
        setAgentPort(Integer.parseInt(property.getString("AgentPort")));
    }

    public String getAgentHost() {
        return AgentHost;
    }

    public void setAgentHost(String agentHost) {
        AgentHost = agentHost;
    }

    public int getAgentPort() {
        return AgentPort;
    }

    public void setAgentPort(int agentPort) {
        AgentPort = agentPort;
    }
}
