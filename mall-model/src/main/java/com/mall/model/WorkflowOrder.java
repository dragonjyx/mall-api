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
@Table(name = "t_workflow_order")
public class WorkflowOrder {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_example_id")
    private String exampleId;

    @Column(name="p_wf_id")
    private Long wfId;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_create_time")
    private Date createTime;

}