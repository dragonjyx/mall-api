package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_account_bill_offline")
public class AccountBillOffline {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_account_id")
    private String accountId;

    @Column(name="p_type")
    private Integer type;// 0:入账 1：提现

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_status")
    private Integer status;//0：冻结 1：解冻 2：取消

    @Column(name="p_amount")
    private BigDecimal amount;

    @Column(name="p_is_delete")
    private Boolean isDelete;

    @Column(name="p_month")
    private String month;//结算月

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_bill_status")
    private Integer billStatus;//0:失败 1：正常
}
