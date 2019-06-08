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
@Table(name = "t_credit_bill")
public class CreditBill {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_name")
    private String name;

    @Column(name="p_adjust_uid")
    private String adjustUid;

    @Column(name="p_adjust_user_name")
    private String adjustUserName;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_amount")
    private BigDecimal amount;//调整额度

    @Column(name="p_create_time")
    private Date createTime;

}
