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
@Table(name = "t_logs")
public class Logs {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_uid")
    private String uid;//用户id

    @Column(name="p_user_name")
    private String userName;//用户名称

    @Column(name="p_platform_code")
    private String platformCode;//平台

    @Column(name="p_module")
    private String module;//模块

    @Column(name="p_method")
    private String method;//方法

    @Column(name="p_action")
    private String action;//执行什么

    @Column(name="p_response_time")
    private Long responseTime;//返回什么

    @Column(name="p_ip")
    private String ipAddress;//ip地址

    @Column(name="p_commit_date")
    private Date commitDate;//提交时间

    @Column(name="p_commit")
    private String commit;//执行描述

    @Column(name="p_params")
    private String params;//执行参数
}