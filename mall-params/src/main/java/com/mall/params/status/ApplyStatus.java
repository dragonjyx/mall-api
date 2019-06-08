package com.mall.params.status;

public enum ApplyStatus {

    CANCEL(0),//申请取消
    UNTREATED(1), //未处理
    TREATING(2), //处理中
    TREATED(3); //已处理
    public int value;
    ApplyStatus(int value) {
        this.value = value;
    }

    public int getApplyStatus() {
        return this.value;
    }

    public static ApplyStatus fromApplyStatus(int status) {
        for (ApplyStatus applyStatus : ApplyStatus.values()) {
            if (applyStatus.getApplyStatus() == status) {
                return applyStatus;
            }
        }
        return null;
    }

}
