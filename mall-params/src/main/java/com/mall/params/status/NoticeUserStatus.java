package com.mall.params.status;

public enum  NoticeUserStatus {
    NO_READ(0),
    READED(1);

    public int value;
    NoticeUserStatus(int value) {
        this.value = value;
    }
}
