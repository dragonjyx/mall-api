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
@Table(name = "t_notice")
public class Notice {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_title")
    private String title;

    @Column(name="p_content")
    private String content;

    @Column(name="p_notice_type")
    private Integer noticeType;//公告类型，1.站内信，2.普通公告',

    @Column(name="p_sender")
    private String sender;

    @Column(name="p_sender_type")
    private Integer senderType;//收件人类型，0：角色，1：手机号

    @Column(name="p_issuer_uid")
    private String issuerUid;

    @Column(name="p_issuer")
    private String issuer;

    @Column(name="p_status")
    private Integer status;//状态0，未发布；1，已发布；3，撤回；

    @Column(name="p_start_time")
    private Date startTime;

    @Column(name="p_end_time")
    private Date endTime;

    @Column(name="p_issue_time")
    private Date issueTime;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;

}