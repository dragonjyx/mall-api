package com.mall.params.status;

public enum  Gender {
    MAIL(0),
    FEMAIL(1),
    UNKNOW(2);

    public int value;
    Gender(int value) {
        this.value = value;
    }
}
