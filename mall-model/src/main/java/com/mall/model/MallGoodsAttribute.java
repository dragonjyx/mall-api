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
@Table(name = "t_mall_goods_attribute")
public class MallGoodsAttribute {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_category_id")
    private Long categoryId;

    @Column(name="p_type_id")
    private Long typeId;

    @Column(name="p_attr_id")
    private Long attrId;

    @Column(name="p_attr_name")
    private String attrName;

    @Column(name="p_attr_value_id")
    private Long attrValueId;

    @Column(name="p_attr_value_name")
    private String attrValueName;
}