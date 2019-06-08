package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSendResp {

    private Long id;

    private String orderSn;

    private String applySn;

    private String contractSn;

    private String agentUid;

    private Integer regionId;

    private String region;

    private String company;

    private Integer storeId;

    private String storeName;

    private Integer assistantUid;

    private String assistant;

    private String phone;

    private String channel;

    private String consignee;

    private String country;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private String zipcode;

    private String mobile;

    private Date createTime;

    private Date updateTime;

    private Integer isUseStock;
}