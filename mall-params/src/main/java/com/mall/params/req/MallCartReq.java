package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallCartReq {

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

    private List<Object> ids;
}