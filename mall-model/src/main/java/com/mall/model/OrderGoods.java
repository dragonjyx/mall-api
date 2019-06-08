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
@Table(name = "t_order_goods")
public class OrderGoods {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_goods_name")
    private String goodsName;

    @Column(name="p_goods_code")
    private String goodsCode;

    @Column(name="p_goods_image")
    private String goodsImage;

    @Column(name="p_num")
    private Integer num;

    @Column(name="p_cost_price")
    private BigDecimal costPrice;

    @Column(name="p_market_price")
    private BigDecimal marketPrice;

    @Column(name="p_price")
    private BigDecimal price;

    @Column(name="p_spec_value")
    private String specValue;

    @Column(name="p_ship_type")
    private Integer shipType;

    @Column(name="p_ship_num")
    private Integer shipNum;

}