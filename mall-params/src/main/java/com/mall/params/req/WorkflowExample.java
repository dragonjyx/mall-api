package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowExample {
    private Integer id;

    private String name;

    private Integer wfId;

    private String uid;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String reamrk;
}