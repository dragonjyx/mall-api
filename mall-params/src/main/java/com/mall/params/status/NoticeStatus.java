package com.mall.params.status;

public enum  NoticeStatus {

    NOT_ISSUE(0),
    ISSUED(1),
    CANCEL_ISSUE(2);

    public int value;
    NoticeStatus(int value) {
        this.value = value;
    }

}
