package com.mall.params.resp;

import lombok.Data;

import java.util.Date;

@Data
public class OrderExchangeResp {

    private Long id;

    private String orderSn;

    private String applySn;

    private String agentUid;

    private String agentName;

    private Integer storeId;

    private String storeName;

    private String storeAddress;

    private String applyInfo;

    private String reason;

    private Date createTime;

    private Date updateTime;

    private String remark;

}
