package com.mall.params.status;

public enum OrderType {

    ONLINE(1), //线上
    OFFLINE(0);//线下


    public int value;

    OrderType(int value) {
        this.value = value;
    }

    public int getOrderType() {
        return this.value;
    }

}
