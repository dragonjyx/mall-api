package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeUserResp {

    private Long id;

    private String issuer;

    private Long noticeId;

    private String title;

    private Date issueTime;

    private Integer isRead;
}