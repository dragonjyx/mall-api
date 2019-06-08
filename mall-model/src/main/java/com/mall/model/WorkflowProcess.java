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
@Table(name = "t_workflow_process")
public class WorkflowProcess {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_node_id")
    private Integer nodeId;

    @Column(name="p_handle_uid")
    private String handleUid;

    @Column(name="p_handle_user_name")
    private String handleUserName;

    @Column(name="p_handle_time")
    private Date handleTime;

    @Column(name="p_handle_data")
    private String handleData;

    @Column(name="p_example_id")
    private Integer exampleId;

    @Column(name="p_example_name")
    private String exampleName;

}