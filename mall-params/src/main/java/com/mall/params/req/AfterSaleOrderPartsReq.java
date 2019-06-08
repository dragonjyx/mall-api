package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleOrderPartsReq {

    private Integer id;

    private String afterSaleOrderSn;

    private Long partsId;

    private String partsName;

    private BigDecimal price;

    private Integer num;
}