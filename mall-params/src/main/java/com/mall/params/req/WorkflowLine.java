package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowLine {
    private Integer id;

    private Integer wfid;

    private String type;

    private String condition;

    private Integer preNode;

    private Integer nextNode;
}