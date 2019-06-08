package com.mall.params.status;

public enum AgentType {
    AGENT(0),
    DESIGNATE(1),
    HQ(2);
    public int value;
    AgentType(int value) {
        this.value = value;
    }

    public int getAgentType() {
        return this.value;
    }

    public static AgentType fromAgentType(int status) {
        for (AgentType agentType : AgentType.values()) {
            if (agentType.getAgentType() == status) {
                return agentType;
            }
        }
        return null;
    }
}
