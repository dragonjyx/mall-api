package com.mall.params.status;

public enum InvType {

    PLAIN(1), //普通发票
    ADDED_VALUE_TAX(2);//增值税发票


    public int value;
    InvType(int value) {
        this.value = value;
    }

    public int getInvType() {
        return this.value;
    }

    public static InvType fromInvType(int type) {
        for (InvType invType : InvType.values()) {
            if (invType.getInvType() == type) {
                return invType;
            }
        }
        return null;
    }

}
