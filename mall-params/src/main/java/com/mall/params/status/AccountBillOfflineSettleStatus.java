package com.mall.params.status;

public enum AccountBillOfflineSettleStatus {

    NO_SETTLE(0),//未结算
    SETTLED(1);//已结算

    public int value;
    AccountBillOfflineSettleStatus(int value) {
        this.value = value;
    }

}
