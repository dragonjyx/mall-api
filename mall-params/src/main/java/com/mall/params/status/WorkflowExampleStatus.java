package com.mall.params.status;

public enum  WorkflowExampleStatus {
    INIT(0),
    PROGRESS(1),
    FINISH(2);

    public int value;
    WorkflowExampleStatus(int value) {
        this.value = value;
    }
}
