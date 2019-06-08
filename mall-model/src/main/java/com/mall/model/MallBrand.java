package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_mall_brand")
public class MallBrand implements Serializable {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_intial")
    private String intial;

    @Column(name="p_logo")
    private String logo;

    @Column(name="p_brand_desc")
    private String brandDesc;

    @Column(name="p_url")
    private String url;

    @Column(name="p_sort")
    private String sort;

    @Column(name="p_category_id")
    private Long categoryId;

    @Column(name="p_is_recommened")
    private Integer isRecommened;

    @Column(name="p_status")
    private Integer status;
}