package com.mall.params.status;

public enum StoreStatus {

    NORMAL(1), //正常
    SHUT(2), //关闭
    WITHDRAWAL(3); //撤场
    public int value;
    StoreStatus(int value) {
        this.value = value;
    }

    public int getStoreStatus() {
        return this.value;
    }

    public static StoreStatus fromStoreStatus(int status) {
        for (StoreStatus storeStatus : StoreStatus.values()) {
            if (storeStatus.getStoreStatus() == status) {
                return storeStatus;
            }
        }
        return null;
    }

}
