package com.mall.params.status;

public enum WorkflowNodeType {
    COMMON(0),
    BRANCH(1);

    public int value;
    WorkflowNodeType(int value) {
        this.value = value;
    }
}
