package com.mall.params.status;

public enum ProduceStatus {

    //提交
    SUBMIT("SUBMIT"),
    //取消
    CANCEL("CANCEL"),
    //生产中
    IN_PRODUCTION("IN_PRODUCTION"),
    //完成
    ACHIEVE("ACHIEVE");
    public String value;
    ProduceStatus(String value) {
        this.value = value;
    }

    public String getProduceStatus() {
        return this.value;
    }

    public static ProduceStatus fromProduceStatus(String status) {
        for (ProduceStatus produceStatus : ProduceStatus.values()) {
            if (produceStatus.getProduceStatus() == status) {
                return produceStatus;
            }
        }
        return null;
    }

}
