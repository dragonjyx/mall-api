package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_mall_cart")
public class MallCart {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_goods_code")
    private String goodsCode;

    @Column(name="p_goods_name")
    private String goodsName;

    @Column(name="p_goods_image")
    private String goodsImage;

    @Column(name="p_market_price")
    private BigDecimal marketPrice;

    @Column(name="p_goods_price")
    private BigDecimal goodsPrice;

    @Column(name="p_buy_num")
    private Integer buyNum;

    @Column(name="p_spec_value")
    private String specValue;

    @Column(name="p_type")
    private String type;

    @Column(name="p_remark")
    private String remark;
}