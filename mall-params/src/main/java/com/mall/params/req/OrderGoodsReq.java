package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoodsReq {

    private Long id;

    private String orderSn;

    private String goodsSn;

    private String goodsName;

    private String goodsCode;

    private Integer num;

    private BigDecimal marketPrice;

    private BigDecimal price;

    private String specValue;

    private Integer shippingType;

    private String shippingNum;

    private Integer shipedNum;

    private Integer isChoice;

    private Integer choiceNumber;
}