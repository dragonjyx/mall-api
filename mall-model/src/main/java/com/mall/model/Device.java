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
@Table(name = "t_device")
public class Device {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_device_id")
    private String deviceId;

    @Column(name="p_device_sn")
    private String deviceSn;

    @Column(name="p_type_id")
    private Long typeId;

    @Column(name="p_type_name")
    private String typeName;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_repertory_id")
    private Long repertoryId;

    @Column(name="p_repertory_name")
    private String repertoryName;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;
}