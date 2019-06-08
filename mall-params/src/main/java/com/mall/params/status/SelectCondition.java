package com.mall.params.status;

public enum SelectCondition {
    
    DEVICE("DEVICE"),//设备
    PLATFORM("PLATFORM"),//平台
    AGENT("DAY"), //代理商
    STORE("MONTH"),//店铺
    DISTRICT("YEAR");//区域
    public String value;
    SelectCondition(String value) {
        this.value = value;
    }

    public String getSelectCondition() {
        return this.value;
    }

    public static SelectCondition fromSelectCondition(String condition) {
        for (SelectCondition selectCondition : SelectCondition.values()) {
            if (selectCondition.getSelectCondition().equals(condition)) {
                return selectCondition;
            }
        }
        return null;
    }

}
