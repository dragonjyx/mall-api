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
@Table(name = "t_workflow_line_logs")
public class WorkflowLineLogs {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_example_id")
    private Long exampleId;

    @Transient
    private String exampleName;

    @Column(name="p_wf_id")
    private Long wfId;

    @Column(name="p_condition")
    private String pCondition;

    @Column(name="p_node_id")
    private Long nodeId;

    @Column(name="p_user_id")
    private String userId;

    @Column(name="p_user_name")
    private String userName;

    @Column(name="p_action")
    private String action;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_handler_users")
    private String handlerUsers;

    @Column(name="p_create_time")
    private Date createTime;
}