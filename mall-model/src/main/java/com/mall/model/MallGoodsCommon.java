package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_mall_goods_common")
public class MallGoodsCommon {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_sn")
    private String sn;

    @Column(name="p_name")
    private String name;

    @Column(name="p_jingle")
    private String jingle;

    @Column(name="p_category_id")
    private Long categoryId;

    @Column(name="p_category_name")
    private String categoryName;

    @Column(name="p_brand_id")
    private Long brandId;

    @Column(name="p_brand_name")
    private String brandName;

    @Column(name="p_type_id")
    private Long typeId;

    @Column(name="p_type_name")
    private String typeName;

    @Column(name="p_image")
    private String image;

    @Column(name="p_common_desc")
    private String commonDesc;

    @Column(name="p_status")
    private String status;

    @Column(name="p_freight")
    private BigDecimal freight;

    @Column(name="p_is_vat")
    private Integer isVat;

    @Column(name="p_is_commend")
    private Integer isCommend;

    @Column(name="p_is_hot")
    private Integer isHot;

    @Column(name="p_region_id")
    private Integer regionId;

    @Column(name="p_region")
    private String region;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_sell_time")
    private Date sellTime;

    @Column(name="p_guarantee")
    private String guarantee;

    @Column(name="p_unit")
    private String unit;

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_is_delete")
    private Integer isDelete;

    @Column(name="p_sell_num")
    private Integer sellNum;

    @Column(name="p_school_id")
    private Long schoolId;

    @Column(name="p_school_name")
    private String schoolName;

    @Column(name="p_school_dorm_id")
    private Long schoolDormId;

    @Column(name="p_school_dorm_num")
    private String schoolDormNum;

    @Column(name="p_school_dorm_name")
    private String schoolDormName;

    @Transient
    private List<MallGoods> mallGoodsList;


}
