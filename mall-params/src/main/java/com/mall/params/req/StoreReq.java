package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreReq {

    private Long id;

    private String name;

    private String uid;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private String storeAbstract;

    private String staff;

    private String score;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}