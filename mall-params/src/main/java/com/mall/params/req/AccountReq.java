package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountReq {

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