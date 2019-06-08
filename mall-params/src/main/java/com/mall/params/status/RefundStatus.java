package com.mall.params.status;

public enum RefundStatus {

    //0 ：正常，1：申请退款中，2：已退款
    NORMAL(0),
    REFUND_REQUEST(1),
    REFUND(2);
    public int value;
    RefundStatus(int value) {
        this.value = value;
    }

}
