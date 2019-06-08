package com.mall.params.status;

public enum PayType {
    WECHAT(0),
    ALIPAY(1),
    OFFLINE(2);
    public int value;
    PayType(int value) {
        this.value = value;
    }
}
