package com.mall.params.status;

public enum RoleStatus {
    DISABLE(0),
    ENABLE(1);

    public int value;
    RoleStatus(int value) {
        this.value = value;
    }
}
