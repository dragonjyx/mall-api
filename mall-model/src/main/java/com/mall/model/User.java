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
@Table(name = "t_user")
public class User {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_username")
    private String username;

    @Column(name="p_password")
    private String password;

    @Column(name="p_key")
    private String pkey;

    @Column(name="p_alias")
    private String alias;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_login_time")
    private Date loginTime;

    @Column(name="p_login_ip")
    private String loginIp;

    @Column(name="p_visit_count")
    private Long visitCount;

    @Column(name="p_error_count")
    private Integer errorCount;

    @Column(name="p_is_account")
    private Boolean isAccount;

    @Column(name="p_is_super")
    private Boolean isSuper;

    @Column(name="p_is_lock")
    private Boolean isLock;

    @Column(name="p_userinfo_id")
    private Long userInfoId;

    @Column(name="p_status")
    private Integer status;//处于什么状态 0：创建 1：已通过审核 2：已冻结

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_unionid")
    private String unionId;//unionId

}