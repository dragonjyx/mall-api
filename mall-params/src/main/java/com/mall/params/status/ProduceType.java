package com.mall.params.status;

public enum ProduceType {

    //自营
    OWM("OWM"),
    //
    OEM("OEM");
    public String value;
    ProduceType(String value) {
        this.value = value;
    }

    public String getProduceType() {
        return this.value;
    }

    public static ProduceType fromProduceType(String type) {
        for (ProduceType produceType : ProduceType.values()) {
            if (produceType.getProduceType() == type) {
                return produceType;
            }
        }
        return null;
    }

}
