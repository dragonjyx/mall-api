package com.mall.params.status;

public enum ChoiceType {

    OWN(1), //自备货
    AGENT(2); //代理商备货
    public int value;
    ChoiceType(int value) {
        this.value = value;
    }

    public int getChoiceType() {
        return this.value;
    }

    public static ChoiceType fromChoiceType(int status) {
        for (ChoiceType choiceType : ChoiceType.values()) {
            if (choiceType.getChoiceType() == status) {
                return choiceType;
            }
        }
        return null;
    }

}
