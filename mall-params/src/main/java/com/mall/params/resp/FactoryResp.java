package com.mall.params.resp;

import lombok.Data;

import java.util.Date;

@Data
public class FactoryResp {

    private Long id;

    private String name;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}