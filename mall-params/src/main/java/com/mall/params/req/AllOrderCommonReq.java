package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllOrderCommonReq {

    private Long id;

    private String uid;

    private String orderSn;

    private Integer orderType;

    private String paymentCode;

    private String paymentName;

    private Date payTime;

    private Date finishedTime;

    private BigDecimal amount;

    private BigDecimal orderAmount;

    private BigDecimal discountAmount;

    private Integer isAccount;

    private BigDecimal accountAmount;

    private BigDecimal creditAccount;

    private Integer integral;

    private BigDecimal shippingFee;

    private String shippingExpress;

    private Date shippingTime;

    private Integer status;

    private Integer refundStatus;

    private Integer sendStatus;

    private Integer isCheck;

    private Integer isDelete;

    private String invoiceNo;

    private String message;

    private String toBuyer;

    private Integer pointsCount;

    private String couponCode;

    private BigDecimal couponPrice;

    private String invPayee;

    private String invContent;

    private Integer invType;

    private String promotionRemark;

    private String permissionCode;

    private String remark;

    private Date createTime;

    private Integer isCredit;

    private String userName;

    private OrderChoiceReq choiceReq;

    private OrderPendulumReq pendulumReq;

    private OrderSendReq sendReq;

    private OrderWithdrawalReq withdrawalReq;

    private OrderExchangeReq exchangeReq;

    private List<OrderGoodsReq> goodsReq;
}