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
@Table(name = "t_produce_order")
public class ProduceOrder {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_sn")
    private String produceSn;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_device_type_id")
    private Long deviceTypeId;

    @Column(name="p_device_type_name")
    private String deviceTypeName;

    @Column(name="p_num")
    private Integer num;

    @Column(name="p_status")
    private String status;

    @Column(name="p_type")
    private String type;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_finished_time")
    private Date finishedTime;

    @Column(name="p_remark")
    private String remark;
}