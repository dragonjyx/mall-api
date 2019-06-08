package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPendulumReq {

    private Long id;

    private String orderSn;

    private String applySn;

    private String contractSn;

    private String agentUid;

    private String agentName;

    private Long storeId;

    private String storeName;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String storeAddress;

    private String assistant1;

    private String phone1;

    private String assistant2;

    private String phone2;

    private Date createTime;

    private Date updateTime;

    private String remark;
}