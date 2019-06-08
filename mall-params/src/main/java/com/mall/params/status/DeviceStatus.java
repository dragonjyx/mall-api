package com.mall.params.status;

public enum DeviceStatus {
    
    //未入库
    NO_PUT_IN(1),
    //已入库
    PUT_IN(2),
    //已出库
    OUT_PUT(3);
    public int value;
    DeviceStatus(int value) {
        this.value = value;
    }

    public int getDeviceStatus() {
        return this.value;
    }

    public static DeviceStatus fromDeviceStatus(int status) {
        for (DeviceStatus deviceStatus : DeviceStatus.values()) {
            if (deviceStatus.getDeviceStatus() == status) {
                return deviceStatus;
            }
        }
        return null;
    }

}
