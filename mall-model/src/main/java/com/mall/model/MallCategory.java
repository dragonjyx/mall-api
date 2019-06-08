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
@Table(name = "t_mall_category")
public class MallCategory {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_category_name")
    private String categoryName;

    @Column(name="p_type_id")
    private Long typeId;

    @Column(name="p_type_name")
    private String typeName;

    @Column(name="p_category_desc")
    private String categoryDesc;

    @Column(name="p_pid")
    private Long pid;

    @Column(name="p_sort")
    private String sort;

    @Column(name="p_status")
    private String status;

    @Column(name="p_remark")
    private String remark;

}