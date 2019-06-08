package com.mall.params.status;

public enum  ResourceType {
    FIRST_LEVEL(1),
    SECOND_LEVEL(2),
    THIRD_LEVEL(3),
    FUNCTION_LEVEL(4);

    public int value;
    ResourceType(int value) {
        this.value = value;
    }
}
