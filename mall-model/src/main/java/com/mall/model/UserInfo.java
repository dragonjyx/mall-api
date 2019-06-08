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
@Table(name = "t_user_info")
public class UserInfo {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_parent_uid")
    private String parentUid;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_name")
    private String name;

    @Column(name="p_id_card")
    private String idCard;

    @Column(name="p_id_card_front_img")
    private String idCardFrontImg;

    @Column(name="p_id_card_back_img")
    private String idCardBackImg;

    @Column(name="p_birthday")
    private String birthday;

    @Column(name="p_email")
    private String email;

    @Column(name="p_sex")
    private Integer sex;//0：男 1：女 2：未知

    @Column(name="p_department")
    private String department;

    @Column(name="p_avatar_url")
    private String avatarUrl;

    @Column(name="p_postition")
    private String postition;

    @Column(name="p_phone")
    private String phone;

    @Column(name="p_address")
    private String address;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_permission_code")
    private String permissionCode;

    @Column(name="p_department_id")
    private Long departmentId;

    @Column(name="p_is_register")
    private Integer isRegister;

    @Column(name="p_type")
    private Integer utype;//0:超级管理员 1:平台管理员 2:代理商 3:配送员 4:供货商

    @Column(name="p_effective_time")
    private Date effectiveTime;

    @Column(name="p_distribution_ratio")
    private BigDecimal distributionRatio;

    @Transient
    private String permissionName;

    @Transient
    private Integer status;//状态

    @Transient
    private String alias;

    @Transient
    private Boolean isAccount;

    @Transient
    private String password;

    @Transient
    private String confirmPassword;

    @Transient
    private Integer isSuper;

}