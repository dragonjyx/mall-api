package com.mall.params.status;

public enum PlatformStatus {
    DISABLE(0),
    ENABLE(1);
    public int value;
    PlatformStatus(int value) {
        this.value = value;
    }
}
