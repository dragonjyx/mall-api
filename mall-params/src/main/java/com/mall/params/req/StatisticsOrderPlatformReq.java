package com.mall.params.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StatisticsOrderPlatformReq {

    private Long id;

    private Integer dateCondition;

    private BigDecimal amount;

    private Integer orderNum;

    private Integer deviceNum;

    private Integer orderType;

    private String earnings;

    private String devNum;

    private Date createTime;

    private Date updateTime;

    private String taskId;

}