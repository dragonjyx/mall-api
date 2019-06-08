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
@Table(name = "t_after_sale_order_parts")
public class AfterSaleOrderParts {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_after_sale_order_sn")
    private String afterSaleOrderSn;

    @Column(name="p_parts_id")
    private Long partsId;

    @Column(name="p_parts_name")
    private String partsName;

    @Column(name="p_price")
    private BigDecimal price;

    @Column(name="p_num")
    private Integer num;
}