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
@Table(name = "t_workflow_example")
public class WorkflowExample {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_example_id")
    private String exampleId;

    @Column(name="p_name")
    private String name;

    @Column(name="p_wf_id")
    private Long wfId;

    @Column(name="p_uid")
    private String uid;//创建者UID

    @Column(name="p_user_name")
    private String userName;//创建人

    @Column(name="p_node_id")
    private Long nodeId;

    @Column(name="p_status")
    private Integer status;//状态扭转 状态 0：初始化，1：进行中，2：完成'

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;
}