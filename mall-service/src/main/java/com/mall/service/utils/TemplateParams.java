package com.mall.service.utils;

/**
 * Created by DRAGON on 2017/10/16.
 */
public class TemplateParams {
    // 参数名称
    private String name;
    // 参数值
    private String value;
    // 颜色
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TemplateParams(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public TemplateParams() {
        super();
    }
}
