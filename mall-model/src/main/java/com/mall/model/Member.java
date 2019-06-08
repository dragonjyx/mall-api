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
@Table(name = "t_member")
public class Member {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="update_date")
    private Date updateDate;

    @Column(name="is_delete")
    private Boolean isDelete;

    @Column(name="remark")
    private String remark;

    @Column(name="member_id")
    private String memberId;

    @Column(name="nickname")
    private String nickname;

    @Column(name="name")
    private String name;

    @Column(name="true_name")
    private String trueName;

    @Column(name="avatar")
    private String avatar;

    @Column(name="gender")
    private Integer gender;

    @Column(name="birthday")
    private String birthday;

    @Column(name="pkey")
    private String pkey;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="email_bind")
    private Byte emailBind;

    @Column(name="mobile")
    private String mobile;

    @Column(name="mobile_bind")
    private Boolean mobileBind;

    @Column(name="qq")
    private String qq;

    @Column(name="wx_union_id")
    private String unionId;

    @Column(name="login_num")
    private Long loginNum;

    @Column(name="register_date")
    private Date registerDate;

    @Column(name="current_login_date")
    private Date currentLoginDate;

    @Column(name="last_login_date")
    private Date lastLoginDate;

    @Column(name="current_login_ip")
    private String currentLoginIp;

    @Column(name="last_login_ip")
    private String lastLoginIp;

    @Column(name="status")
    private Integer status;

    @Column(name="address")
    private String address;

    @Column(name="share_member_id")
    private String shareMemberId;

    @Column(name="login_error_times")
    private Integer loginErrorTimes;

    @Column(name="register_ip")
    private String registerIp;

    @Column(name="wechat")
    private String wechat;

}