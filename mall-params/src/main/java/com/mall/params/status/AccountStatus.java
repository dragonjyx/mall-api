package com.mall.params.status;

public enum AccountStatus {

    DISABLE(0),
    ENABLE(1);

    public int value;
    AccountStatus(int value) {
        this.value = value;
    }

}
