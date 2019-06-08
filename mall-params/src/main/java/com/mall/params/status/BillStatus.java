package com.mall.params.status;

public enum BillStatus {
    FAIL(0),
    SUCCESS(1);

    public int value;
    BillStatus(int value) {
        this.value = value;
    }

}
