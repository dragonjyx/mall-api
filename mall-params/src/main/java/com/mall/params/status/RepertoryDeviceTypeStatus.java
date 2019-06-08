package com.mall.params.status;

public enum RepertoryDeviceTypeStatus {
    //缺货
    STOCKOUT(1),
    //警告
    WARN(2),
    //正常
    NORMAL(3);
    public int value;
    RepertoryDeviceTypeStatus(int value) {
        this.value = value;
    }

    public int getRepertoryDeviceTypeStatus() {
        return this.value;
    }

    public static RepertoryDeviceTypeStatus fromRepertoryDeviceTypeStatus(int status) {
        for (RepertoryDeviceTypeStatus repertoryDeviceTypeStatus : RepertoryDeviceTypeStatus.values()) {
            if (repertoryDeviceTypeStatus.getRepertoryDeviceTypeStatus() == status) {
                return repertoryDeviceTypeStatus;
            }
        }
        return null;
    }

}
