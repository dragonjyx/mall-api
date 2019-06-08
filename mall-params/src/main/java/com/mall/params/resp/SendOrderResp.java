package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendOrderResp {

    private Long id;

    private String orderSn;

    private String sn;

    private String sender;

    private Integer uid;

    private String phone;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private String status;

    private Date createTime;

    private Date updateTime;

    private Date sendTime;

    private Date finishedTime;

    private String remark;
}