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
@Table(name = "t_after_sale_template")
public class AfterSaleTemplate {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_device_type_id")
    private Long deviceTypeId;

    @Column(name="p_err_type")
    private String errType;

    @Column(name="p_err_icon")
    private String errIcon;

    @Column(name="p_err_icon_click")
    private String errIconClick;
}