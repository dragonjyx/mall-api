package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleApplyResp {
    private Integer id;

    private String orderSn;

    private String deviceSn;

    private String uid;

    private String agentId;

    private Integer agentType;

    private String types;

    private String detail;

    private String imgUrls;

    private Integer status;

    private String dispenseStatus;

    private Byte isEvaluation;

    private String name;

    private String phone;

    private String province;

    private String city;

    private String district;

    private String address;

    private Date createTime;

    private Date finishedTime;

    private Integer isWarranty;

    private String remark;
}