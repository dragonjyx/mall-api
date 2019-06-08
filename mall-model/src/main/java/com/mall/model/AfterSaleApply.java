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
@Table(name = "t_after_sale_apply")
public class AfterSaleApply {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_device_sn")
    private String deviceSn;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_agent_id")
    private Integer agentId;

    @Column(name="p_agent_type")
    private Integer agentType;

    @Column(name="p_types")
    private String types;

    @Column(name="p_detail")
    private String detail;

    @Column(name="p_img_urls")
    private String imgUrls;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_dispense_status")
    private Integer dispenseStatus;

    @Column(name="p_is_evaluation")
    private Integer isEvaluation;

    @Column(name="p_name")
    private String name;

    @Column(name="p_phone")
    private String phone;

    @Column(name="p_province")
    private String province;

    @Column(name="p_city")
    private String city;

    @Column(name="p_district")
    private String district;

    @Column(name="p_address")
    private String address;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_finished_time")
    private Date finishedTime;

    @Column(name="p_is_warranty")
    private Integer isWarranty;

    @Column(name="p_remark")
    private String remark;
}