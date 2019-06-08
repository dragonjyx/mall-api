package com.mall.params.status;

public enum WarrantyStatus {

    UN_WARRANTY(0), //不保修
    WARRANTY(1); //保修
    public int value;
    WarrantyStatus(int value) {
        this.value = value;
    }

    public int getWarrantyStatus() {
        return this.value;
    }

    public static WarrantyStatus fromWarrantyStatus(int status) {
        for (WarrantyStatus warrantyStatus : WarrantyStatus.values()) {
            if (warrantyStatus.getWarrantyStatus() == status) {
                return warrantyStatus;
            }
        }
        return null;
    }

}
