package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoodsDeviceResp {
    private Integer id;

    private String orderSn;

    private String goodsSn;

    private String goodCode;

    private String deviceSn;

    private String agentUid;

    private Date guaranteeTime;
}