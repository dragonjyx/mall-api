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
@Table(name = "t_store")
public class Store {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_province")
    private String province;

    @Column(name="p_province_name")
    private String provinceName;

    @Column(name="p_city")
    private String city;

    @Column(name="p_city_name")
    private String cityName;

    @Column(name="p_district")
    private String district;

    @Column(name="p_district_name")
    private String districtName;

    @Column(name="p_address")
    private String address;

    @Column(name="p_store_abstract")
    private String storeAbstract;

    @Column(name="p_staff")
    private String staff;

    @Column(name="p_score")
    private String score;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_status")
    private Integer status;
}