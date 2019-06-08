package com.mall.params.status;

public enum CustomerStatus {

    //在线
    ONLINE("ONLINE"),
    //离线
    OFFLINE("OFFLINE");
    public String value;
    CustomerStatus(String value) {
        this.value = value;
    }

    public String getCustomerStatus() {
        return this.value;
    }

    public static CustomerStatus fromCustomerStatus(String status) {
        for (CustomerStatus customerStatus : CustomerStatus.values()) {
            if (customerStatus.getCustomerStatus() == status) {
                return customerStatus;
            }
        }
        return null;
    }

}
