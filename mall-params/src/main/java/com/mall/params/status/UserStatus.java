package com.mall.params.status;

public enum UserStatus {
    INIT(0),
    CHECKED(1),
    FREEZE(2);

    public int value;
    UserStatus(int value) {
        this.value = value;
    }
}
