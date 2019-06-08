package com.mall.params.status;

public enum  WorkflowNodeHandlerType {
    ROLE(0),
    USER(1);

    public int value;
    WorkflowNodeHandlerType(int value) {
        this.value = value;
    }
}
