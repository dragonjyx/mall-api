package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_send_order_device")
public class SendOrderDevice {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_send_sn")
    private String sendSn;

    @Column(name="p_device_type_id")
    private Integer deviceTypeId;

    @Column(name="p_device_type_name")
    private String deviceTypeName;

    @Column(name="p_device_sn")
    private String deviceSn;
}