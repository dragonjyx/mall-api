package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_workflow_line")
public class WorkflowLine {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_wf_id")
    private Long wfId;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_condition")
    private String pCondition;

    @Column(name="p_node_id")
    private Long nodeId;

    @Column(name="p_pre_node_id")
    private Long preNodeId;

    @Column(name="p_next_node_id")
    private Long nextNodeId;

    @Transient
    private String nodeName;

    @Transient
    private String userId;

    @Transient
    private String userName;

    @Transient
    private String action;

    @Transient
    private Integer status;

}