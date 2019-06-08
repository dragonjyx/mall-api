package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepertoryResp {

    private Long id;

    private String uid;

    private String name;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private String abstracts;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark;
}