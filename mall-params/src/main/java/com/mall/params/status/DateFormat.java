package com.mall.params.status;

public enum DateFormat {

    HOUR("HOUR"),//时
    DAY("DAY"), //天
    MONTH("MONTH"),//月
    YEAR("YEAR");//年
    public String value;
    DateFormat(String value) {
        this.value = value;
    }

    public String getDateFormat() {
        return this.value;
    }

    public static DateFormat fromDateFormat(String format) {
        for (DateFormat dateFormat : DateFormat.values()) {
            if (dateFormat.getDateFormat().equals(format)) {
                return dateFormat;
            }
        }
        return null;
    }

}
