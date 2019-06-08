package com.mall.params.status;

public enum OrderSendStatus {

    UNSHIPPED(1),
    SHIPPED(2),
    PORTION(3);
    public int value;
    OrderSendStatus(int value) {
        this.value = value;
    }

    public int getOrderSendStatus() {
        return this.value;
    }

    public static OrderSendStatus fromOrderSendStatus(int status) {
        for (OrderSendStatus orderSentStatus : OrderSendStatus.values()) {
            if (orderSentStatus.getOrderSendStatus() == status) {
                return orderSentStatus;
            }
        }
        return null;
    }

}
