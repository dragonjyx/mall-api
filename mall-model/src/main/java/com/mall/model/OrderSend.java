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
@Table(name = "t_order_send")
public class OrderSend {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_apply_sn")
    private String applySn;

    @Column(name="p_contract_sn")
    private String contractSn;

    @Column(name="p_agent_uid")
    private String agentUid;

    @Column(name="p_region_id")
    private Integer regionId;

    @Column(name="p_region")
    private String region;

    @Column(name="p_company")
    private String company;

    @Column(name="p_store_id")
    private Integer storeId;

    @Column(name="p_store_name")
    private String storeName;

    @Column(name="p_assistant_uid")
    private Integer assistantUid;

    @Column(name="p_assistant")
    private String assistant;

    @Column(name="p_phone")
    private String phone;

    @Column(name="p_channel")
    private String channel;

    @Column(name="p_consignee")
    private String consignee;

    @Column(name="p_country")
    private String country;

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

    @Column(name="p_zipcode")
    private String zipcode;

    @Column(name="p_mobile")
    private String mobile;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_is_use_stock")
    private Integer isUseStock;
}