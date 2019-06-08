package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_after_sale_order")
public class AfterSaleOrder {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_after_sale_sn")
    private String afterSaleSn;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_user_name")
    private String userName;

    @Column(name="p_after_apply_id")
    private Long afterApplyId;

    @Column(name="p_amount")
    private String amount;

    @Column(name="p_payment_code")
    private String paymentCode;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_pay_time")
    private Date payTime;

    @Column(name="p_remark")
    private String remark;
}