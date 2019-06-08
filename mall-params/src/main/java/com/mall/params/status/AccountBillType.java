package com.mall.params.status;

public enum  AccountBillType {
    ENTER(0),
    WIDTHDRAW(1);

    public int value;
    AccountBillType(int value) {
        this.value = value;
    }
}
