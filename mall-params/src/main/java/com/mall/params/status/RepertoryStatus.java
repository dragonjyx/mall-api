package com.mall.params.status;

public enum RepertoryStatus {
    //正常
    NORMAL(1),
    //关闭
    CLOSE(2);
    public int value;
    RepertoryStatus(int value) {
        this.value = value;
    }

    public int getRepertoryStatus() {
        return this.value;
    }

    public static RepertoryStatus fromRepertoryStatus(int status) {
        for (RepertoryStatus repertoryStatus : RepertoryStatus.values()) {
            if (repertoryStatus.getRepertoryStatus() == status) {
                return repertoryStatus;
            }
        }
        return null;
    }

}
