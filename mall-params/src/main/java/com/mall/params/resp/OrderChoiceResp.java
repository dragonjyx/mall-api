package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderChoiceResp {
    private Long id;

    private String orderSn;

    private String applySn;

    private String contractSn;

    private String agentUid;

    private String userName;

    private Integer repertoryId;

    private String repertoryName;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private String phone;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private Integer choiceType;
    
}