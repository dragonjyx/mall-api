package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendOrderReq {

    private Long id;

    private String orderSn;

    private String sendSn;

    private String sender;

    private String uid;

    private String phone;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date sendTime;

    private Date finishedTime;

    private String remark;

    private List<String> deviceSns;
}