package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleOrderReq {

    private Long id;

    private String afterSaleSn;

    private String uid;

    private String userName;

    private Long afterApplyId;

    private BigDecimal amount;

    private String paymentCode;

    private Integer status;

    private Date createTime;

    private Date payTime;

    private String remark;

    private List<AfterSaleOrderPartsReq> partsReqs;
}