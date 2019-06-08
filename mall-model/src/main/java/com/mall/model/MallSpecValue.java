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
@Table(name = "t_mall_spec_value")
public class MallSpecValue {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_code")
    private String code;

    @Column(name="p_name")
    private String name;

    @Column(name="p_spec_id")
    private Long specId;

    @Column(name="p_category_id")
    private Long categoryId;

    @Column(name="p_sort")
    private Integer sort;
}