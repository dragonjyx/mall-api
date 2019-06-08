package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowNode {
    private Integer id;

    private String name;

    private String action;

    private String data;

    private String handler;

    private String type;

    private String branch;

    private Date createTime;

    private Date updateTime;

    private String remark;
}