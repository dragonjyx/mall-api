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
@Table(name = "t_workflow_node")
public class WorkflowNode {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_action")
    private String action;

    @Column(name="p_data")
    private String data;

    @Column(name="p_handler_type")
    private Integer handlerType;

    @Column(name="p_handler")
    private String handler;

    @Column(name="p_handler_name")
    private String handlerName;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_branch")
    private Integer branch;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;
}