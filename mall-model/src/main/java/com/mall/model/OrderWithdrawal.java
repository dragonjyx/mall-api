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
@Table(name = "t_order_withdrawal")
public class OrderWithdrawal {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_apply_sn")
    private String applySn;

    @Column(name="p_agent_uid")
    private String agentUid;

    @Column(name="p_agent_name")
    private String agentName;

    @Column(name="p_origin_store_id")
    private Integer originStoreId;

    @Column(name="p_origin_store_name")
    private String originStoreName;

    @Column(name="p_origin_store_address")
    private String originStoreAddress;

    @Column(name="p_show_time")
    private String showTime;

    @Column(name="p_reason")
    private String reason;

    @Column(name="p_new_store_id")
    private Integer newStoreId;

    @Column(name="p_new_store_name")
    private String newStoreName;

    @Column(name="p_new_store_address")
    private String newStoreAddress;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;
}