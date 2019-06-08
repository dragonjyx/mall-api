package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_store_show")
public class StoreShow {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_store_id")
    private Long storeId;

    @Column(name="p_device_type_id")
    private Integer deviceTypeId;

    @Column(name="p_device_type_name")
    private String deviceTypeName;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_goods_code")
    private String goodsCode;

    @Column(name="p_device_sn")
    private String deviceSn;

    @Column(name="p_show_start")
    private Date showStart;

    @Column(name="p_show_end")
    private Date showEnd;
}