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
@Table(name = "t_mall_address")
public class MallAddress {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_address_sn")
    private String addressSn;

    @Column(name="p_consignee")
    private String consignee;

    @Column(name="p_country")
    private String country;

    @Column(name="p_province")
    private String province;

    @Column(name="p_city")
    private String city;

    @Column(name="p_district")
    private String district;

    @Column(name="p_address")
    private String address;

    @Column(name="p_zipcode")
    private String zipcode;

    @Column(name="p_phone")
    private String phone;

    @Column(name="p_receiver_name")
    private String receiverName;

    @Column(name="p_school_id")
    private Long schoolId;

    @Column(name="p_school_name")
    private String schoolName;

    @Column(name="p_school_dorm_id")
    private Long schoolDormId;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_dorm_name")
    private String dormName;

    @Column(name="p_is_default")
    private Integer isDefault;
}