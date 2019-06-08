package com.mall.params.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StatisticsOrderAgentReq {

    private Long id;

    private Integer dateCondition;

    private String agentUid;

    private String agentName;

    private BigDecimal amount;

    private Integer orderNum;

    private Integer deviceNum;

    private String earnings;

    private String devNum;

    private Date createTime;

    private Date updateTime;

    private String taskId;

}