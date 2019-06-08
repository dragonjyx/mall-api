package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_wechat_user_info")
public class WechatUserInfo implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//自增id

    @Column(name = "open_id")
    private String openId;//用户OpenID

    @Column(name = "union_id")
    private String unionId;//unionId

    @Column(name = "nickname")
    private String nickname;//用户昵称

    @Column(name = "sex")
    private String sex;//用户性别

    @Column(name = "city")
    private String city;//用户所在城市

    @Column(name = "country")
    private String country;//用户所在国家

    @Column(name = "province")
    private String province;//用户所在省份

    @Column(name = "language")
    private String language;//语言

    @Column(name = "headimgurl")
    private String headimgurl;//头像地址

    @Column(name = "subscribe_time")
    private String subscribe_time;//关注时间

    @Column(name = "phone_num")
    private String phoneNum;//手机号

    @Column(name = "name")
    private String name;//用户名称

}
