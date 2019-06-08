package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallCartResp {

    private Long id;

    private String uid;

    private String goodsSn;

    private String goodsCode;

    private String goodsName;

    private String goodsImage;

    private BigDecimal marketPrice;

    private BigDecimal goodsPrice;

    private Integer buyNum;

    private String specValue;

    private String type;

    private String remark;

    private MallGoodsCommonResp commonResp;

    private MallGoodsResp goodsResp;
}