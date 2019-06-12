package com.mall.params.status;

public enum BillStatus {
    INIT(0),
    SUCCESS(1),
    FAIL(2);

    public int value;
    BillStatus(int value) {
        this.value = value;
    }

}
