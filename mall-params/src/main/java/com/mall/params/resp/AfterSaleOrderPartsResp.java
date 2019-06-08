package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleOrderPartsResp {

    private Integer id;

    private String afterSaleOrderSn;

    private Integer partsId;

    private String partsName;

    private BigDecimal price;

    private Integer num;
}