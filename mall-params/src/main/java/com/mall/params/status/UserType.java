package com.mall.params.status;

public enum UserType {
    SUPER(0),//超级管理员
    PLATFORM(1),//平台管理员
    AGENT(2),//代理商
    DELIVERY(3),//配送员
    SUPPLIER(4);//供货商
    
    public int value;
    UserType(int value) {
        this.value = value;
    }

    public int getUserType() {
        return this.value;
    }

    public static UserType fromUserType(int value) {
        for (UserType userType : UserType.values()) {
            if (userType.getUserType() == value) {
                return userType;
            }
        }
        return null;
    }
}
