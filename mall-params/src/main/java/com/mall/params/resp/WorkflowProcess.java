package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowProcess {
    private Integer id;

    private Integer nodeId;

    private String handleUid;

    private Date handleTime;

    private String handleData;

    private Integer exid;
}