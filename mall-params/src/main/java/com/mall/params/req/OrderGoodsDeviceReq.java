package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoodsDeviceReq {

    private Long id;

    private String orderSn;

    private String goodsSn;

    private String goodCode;

    private String deviceSn;

    private String agentUid;

    private Date guaranteeTime;
}