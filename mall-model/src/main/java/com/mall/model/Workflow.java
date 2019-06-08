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
@Table(name = "t_workflow")
public class Workflow {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_wf_no")
    private String wfNo;

    @Column(name="p_name")
    private String name;

    @Column(name="p_node_list")
    private String nodeList;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_is_default")
    private Integer isDefault;
}