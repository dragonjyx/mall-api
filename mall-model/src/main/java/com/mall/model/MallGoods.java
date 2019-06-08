package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_mall_goods")
public class MallGoods {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_code")
    private String code;

    @Column(name="p_name")
    private String name;

    @Column(name="p_bar_code")
    private String barCode;

    @Column(name="p_goods_image")
    private String goodsImage;

    @Column(name="p_spec_ids")
    private String specIds;

    @Column(name="p_spec_codes")
    private String specCodes;

    @Column(name="p_spec_values")
    private String specValues;

    @Column(name="p_cost_price")
    private BigDecimal costPrice;

    @Column(name="p_market_price")
    private BigDecimal marketPrice;

    @Column(name="p_price")
    private BigDecimal price;

    @Column(name="p_is_promotion")
    private Integer isPromotion;

    @Column(name="p_promotion_price")
    private BigDecimal promotionPrice;

    @Column(name="p_promotion_type")
    private String promotionType;

    @Column(name="p_promotion_start")
    private Date promotionStart;

    @Column(name="p_promotion_end")
    private Date promotionEnd;

    @Column(name="p_limit_num")
    private Integer limitNum;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_permission_code")
    private Integer permissionCode;

    @Column(name="p_permission_name")
    private String permissionName;

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_stock_num")
    private Integer stockNum;

    @Column(name="p_is_delete")
    private Integer isDelete;

    @Column(name="p_sell_num")
    private Integer sellNum;

    @Column(name="p_device_type_id")
    private Long deviceTypeId;

    @Column(name="p_device_type_name")
    private String deviceTypeName;

    @Column(name="p_factory_id")
    private Long factoryId;

    @Column(name="p_factory_name")
    private String factoryName;
}