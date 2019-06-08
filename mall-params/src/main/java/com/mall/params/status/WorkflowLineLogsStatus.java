package com.mall.params.status;

public enum  WorkflowLineLogsStatus {
    INIT(0),
    PASS(1),
    REJECT(2);

    public int value;
    WorkflowLineLogsStatus(int value) {
        this.value = value;
    }
}
