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
@Table(name = "t_mall_goods_gallery")
public class MallGoodsGallery {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_img_url")
    private String imgUrl;

    @Column(name="p_img_desc")
    private String imgDesc;

    @Column(name="p_thumb_url")
    private String thumbUrl;

    @Column(name="p_img_original")
    private String imgOriginal;
}