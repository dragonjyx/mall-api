package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoodsResp {
    private Integer id;

    private String orderSn;

    private String goodsSn;

    private String goodsName;

    private String goodsCode;

    private Integer num;

    private BigDecimal marketPrice;

    private BigDecimal price;

    private String specValue;

    private String shippingType;

    private String shippingNum;

    private Integer shipedNum;

    private Byte isChoice;

    private Integer choiceNumber;
}