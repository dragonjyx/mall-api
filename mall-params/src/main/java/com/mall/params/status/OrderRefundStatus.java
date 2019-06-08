package com.mall.params.status;

public enum OrderRefundStatus {

    NOTREFUND(1),
    REBATES(2),
    REFUND(3);
    public int value;
    OrderRefundStatus(int value) {
        this.value = value;
    }

    public int getOrderRefundStatus() {
        return this.value;
    }

    public static OrderRefundStatus fromOrderType(int status) {
        for (OrderRefundStatus orderRefundStatus : OrderRefundStatus.values()) {
            if (orderRefundStatus.getOrderRefundStatus() == status) {
                return orderRefundStatus;
            }
        }
        return null;
    }

}
