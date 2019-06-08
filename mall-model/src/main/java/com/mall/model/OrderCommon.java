package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_order_common")
public class OrderCommon {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_member_id")
    private String memberId;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_trade_no")
    private String tradeNo;

    @Column(name="p_pay_type")
    private Integer payType;

    @Column(name="p_pay_time")
    private Date payTime;

    @Column(name="p_finished_time")
    private Date finishedTime;

    @Column(name="p_amount")
    private BigDecimal amount;

    @Column(name="p_order_amount")
    private BigDecimal orderAmount;

    @Column(name="p_discount_amount")
    private BigDecimal discountAmount;

    @Column(name="p_is_account")
    private Integer isAccount;

    @Column(name="p_account_amount")
    private BigDecimal accountAmount;

    @Column(name="p_integral")
    private Integer integral;

    @Column(name="p_ship_fee")
    private BigDecimal shipFee;

    @Column(name="p_country")
    private String country;

    @Column(name="p_province")
    private String province;

    @Column(name="p_city")
    private String city;

    @Column(name="p_district")
    private String district;

    @Column(name="p_school_name")
    private String schoolName;

    @Column(name="p_school_id")
    private Long schoolId;

    @Column(name="p_dorm_name")
    private String dormName;

    @Column(name="p_dorm_id")
    private Long dormId;

    @Column(name="p_address")
    private String address;

    @Column(name="p_ship_time")
    private Date shipTime;

    @Column(name="p_status")
    private Integer status;//状态，0：待支付，1：已支付(待配送)，2：已支付(配送中)，3：已支付（配送完成），4：订单完成， 5：已取消 6：已删除

    @Column(name="p_refund_status")
    private Integer refundStatus; // 0 ：正常，1：申请退款中，2：已退款

    @Column(name="p_is_delete")
    private Integer isDelete;

    @Column(name="p_invoice_no")
    private String invoiceNo;

    @Column(name="p_message")
    private String message;

    @Column(name="p_to_buyer")
    private String toBuyer;

    @Column(name="p_points_count")
    private Integer pointsCount;

    @Column(name="p_coupon_code")
    private String couponCode;

    @Column(name="p_coupon_price")
    private BigDecimal couponPrice;

    @Column(name="p_inv_payee")
    private String invPayee;

    @Column(name="p_inv_content")
    private String invContent;

    @Column(name="p_inv_type")
    private Integer invType;

    @Column(name="p_promotion_remark")
    private String promotionRemark;

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_phone_num")
    private String phoneNum;

    @Column(name="p_receiver_name")
    private String receiverName;

    @Transient
    private List<OrderGoods> orderGoodsList;//订单商品

}