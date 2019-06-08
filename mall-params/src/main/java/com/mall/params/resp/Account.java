package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Integer id;

    private String aid;

    private String uid;

    private String idCardZ;

    private String idCardF;

    private String licenseUrl;

    private Integer regionId;

    private String region;

    private String contractNum;

    private String contractImg;

    private BigDecimal money;

    private BigDecimal forzenMoney;

    private BigDecimal allAmount;

    private Integer rankPoints;

    private BigDecimal credit;

    private BigDecimal creditUsed;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark;
}