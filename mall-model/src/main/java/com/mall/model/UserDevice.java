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
@Table(name = "t_user_device")
public class UserDevice {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_device_id")
    private String deviceId;

    @Column(name="p_device_type_id")
    private Integer deviceTypeId;

    @Column(name="p_dev_type_name")
    private String devTypeName;

    @Column(name="p_uid")
    private Integer uid;

    @Column(name="p_add_time")
    private Date addTime;
}