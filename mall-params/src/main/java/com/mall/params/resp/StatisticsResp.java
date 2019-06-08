package com.mall.params.resp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsResp {

    private Integer countResp;

    private Integer orderType;

    private BigDecimal amount;

    private BigDecimal orderAmount;

    private String agentUid;

    private String agentName;

    private Long storeId;

    private Long deviceTypeId;

    private BigDecimal price;

    private BigDecimal marketPrice;

}
