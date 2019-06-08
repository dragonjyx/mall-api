package com.mall.params.status;

public enum CreditBillType {
    ADJUST(0),
    CONSUME(1);

    public int value;
    CreditBillType(int value) {
        this.value = value;
    }
}
