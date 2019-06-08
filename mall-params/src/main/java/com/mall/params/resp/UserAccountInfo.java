package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInfo {

    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String aid;

    private String uid;

    private String idCardZ;

    private String idCardF;

    private String licenseUrl;

    private String regionId;

    private String region;

    private String contractNum;

    private String contractImg;

    private BigDecimal money;

    private BigDecimal forzenMoney;

    private BigDecimal allAmount;

    private Integer rankPoints;

    private BigDecimal credit;

    private BigDecimal creditUsed;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private String version;
}