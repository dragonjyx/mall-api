package com.mall.params.status;

public enum FactoryStatus {

    NORMAL(1), //正常
    SHUT(2); //关闭
    public int value;
    FactoryStatus(int value) {
        this.value = value;
    }

    public int getFactoryStatus() {
        return this.value;
    }

    public static FactoryStatus fromFactoryStatus(int status) {
        for (FactoryStatus factoryStatus : FactoryStatus.values()) {
            if (factoryStatus.getFactoryStatus() == status) {
                return factoryStatus;
            }
        }
        return null;
    }

}
