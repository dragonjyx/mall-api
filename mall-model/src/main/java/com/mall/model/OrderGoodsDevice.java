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
@Table(name = "t_order_goods_device")
public class OrderGoodsDevice {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_goods_code")
    private String goodCode;

    @Column(name="p_device_sn")
    private String deviceSn;

    @Column(name="p_agent_uid")
    private String agentUid;

    @Column(name="p_guarantee_time")
    private Date guaranteeTime;
}