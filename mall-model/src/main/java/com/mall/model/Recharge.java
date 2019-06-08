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
@Table(name = "t_recharge")
public class Recharge {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_sn")
    private String sn;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_name")
    private String name;

    @Column(name="p_recharge_uid")
    private String rechargeUid;

    @Column(name="p_recharge_user_name")
    private String rechargeName;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_payment_code")
    private Integer paymentCode;

    @Column(name="p_voucher")
    private String voucher;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_remark")
    private String remark;
}