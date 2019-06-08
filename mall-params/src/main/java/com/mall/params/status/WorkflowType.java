package com.mall.params.status;

public class WorkflowType {

    public static final int SEND = 1; //送货审核
    public static final int CHOICE = 2; //备货审核
    public static final int PENDULUM = 5;//摆样审核

    public static Integer getTypeByOrderType(Integer type) {
        switch (type) {
            case 1:
                return SEND;
            case 2:
                return CHOICE;
            case 3:
                return PENDULUM;
            default:
                return null;
        }
    }

    public static Integer getType(Integer type) {
        switch (type) {
            case SEND:
                return SEND;
            case CHOICE:
                return CHOICE;
            case PENDULUM:
                return PENDULUM;
            default:
                return null;
        }
    }

}
