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
@Table(name = "t_notice_user")
public class NoticeUser {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_notice_id")
    private Long noticeId;

    @Column(name="p_status")
    private Integer status;//状态0，未读；1，已读',

    @Column(name="p_update_time")
    private Date updateTime;

}