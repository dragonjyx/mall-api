package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleOrderResp {

    private Long id;

    private String afterSaleSn;

    private String uid;

    private String userName;

    private Long afterApplyId;

    private String amount;

    private String paymentCode;

    private Integer status;

    private Date createTime;

    private Date payTime;

    private String remark;

    private List<AfterSaleOrderPartsResp> partsResps;
}