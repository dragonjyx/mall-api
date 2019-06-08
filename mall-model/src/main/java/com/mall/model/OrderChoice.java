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
@Table(name = "t_order_choice")
public class OrderChoice {

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

    @Column(name="p_user_name")
    private String userName;

    @Column(name="p_repertory_id")
    private Integer repertoryId;

    @Column(name="p_repertory_name")
    private String repertoryName;

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

    @Column(name="p_phone")
    private String phone;

    @Column(name="p_crate_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_choice_type")
    private Integer choiceType;
    
}