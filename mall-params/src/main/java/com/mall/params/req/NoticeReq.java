package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeReq {

    private Long id;

    private String title;

    private String content;

    private Integer noticeType;

    private String sender;

    private Integer senderType;

    private String issuerUid;

    private String issuer;

    private Integer status;

    private Date startTime;

    private Date endTime;

    private Date issueTime;

    private Date createTime;

    private Date updateTime;

    private String remark;

}