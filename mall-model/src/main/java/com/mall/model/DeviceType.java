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
@Table(name = "t_device_type")
public class DeviceType {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_device_category_id")
    private Long deviceCategoryId;

    @Column(name="p_device_category_name")
    private String deviceCategoryName;

    @Column(name="p_factory_id")
    private Long factoryId;

    @Column(name="p_factory_name")
    private String factoryName;

    @Column(name="p_model")
    private String model;

    @Column(name="p_remark")
    private String remark;
}