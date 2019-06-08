package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleApplyReq {

    private Long id;

    private String orderSn;

    private String deviceSn;

    private String uid;

    private String agentId;

    private Integer agentType;

    private String types;

    private String detail;

    private String imgUrls;

    private Integer status;

    private Integer dispenseStatus;

    private Integer isEvaluation;

    private String name;

    private String phone;

    private String province;

    private String city;

    private String district;

    private String address;

    private Date createTime;

    private Date finishedTime;

    private String remark;

    private Integer isWarranty;

    private List<AfterManApplyReq> manApplyReqs;
}