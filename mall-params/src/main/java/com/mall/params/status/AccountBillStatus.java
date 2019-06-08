package com.mall.params.status;

public enum  AccountBillStatus {
    FREE(0),
    UNFREE(1),
    CANCEL(2);

    public int value;
    AccountBillStatus(int value) {
        this.value = value;
    }

}
