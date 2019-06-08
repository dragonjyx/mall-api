package com.mall.params.req;

import lombok.Data;

import java.util.List;

@Data
public class AllSendOrderReq {

    private String orderSn;

    private List<OrderGoodsDeviceReq> goodsDeviceReqs;

}
