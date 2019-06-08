package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderWithdrawalResp {
    private Integer id;

    private String orderSn;

    private String applySn;

    private String agentUid;

    private String agentName;

    private Integer originStoreId;

    private String originStoreName;

    private String originStoreAddress;

    private String showTime;

    private String reason;

    private Integer newStoreId;

    private String newStoreName;

    private String newStoreAddress;

    private Date createTime;

    private Date updateTime;

    private String remark;
}