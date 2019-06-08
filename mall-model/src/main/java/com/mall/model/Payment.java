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
@Table(name = "t_payment")
public class Payment {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_code")
    private String code;

    @Column(name="p_name")
    private String name;

    @Column(name="p_icon")
    private String icon;

    @Column(name="p_info")
    private String info;

    @Column(name="p_config")
    private String config;

    @Column(name="p_is_online")
    private Boolean isOnline;

    @Column(name="p_status")
    private Integer status;
}