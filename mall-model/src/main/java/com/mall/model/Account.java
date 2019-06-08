package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_account")
public class Account {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_aid")
    private String aid;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_id_card_z")
    private String idCardZ;

    @Column(name="p_id_card_f")
    private String idCardF;

    @Column(name="p_license_url")
    private String licenseUrl;

    @Column(name="p_region_id")
    private String regionId;

    @Column(name="p_region")
    private String region;

    @Column(name="p_contract_num")
    private String contractNum;

    @Column(name="p_contract_img")
    private String contractImg;

    @Column(name="p_money")
    private BigDecimal money;

    @Column(name="p_forzen_money")
    private BigDecimal forzenMoney;

    @Column(name="p_all_amount")
    private BigDecimal allAmount;

    @Column(name="p_rank_points")
    private Integer rankPoints;

    @Column(name="p_credit")
    private BigDecimal credit;

    @Column(name="p_credit_used")
    private BigDecimal creditUsed;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_version")
    private Integer version;
}
