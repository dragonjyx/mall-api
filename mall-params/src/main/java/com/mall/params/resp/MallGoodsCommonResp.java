package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallGoodsCommonResp {

    private Long id;

    private String sn;

    private String name;

    private String jingle;

    private Long categoryId;

    private String categoryName;

    private Long brandId;

    private String brandName;

    private Long typeId;

    private String image;

    private String commonDesc;

    private String status;

    private BigDecimal freight;

    private Integer isVat;

    private Integer isCommend;

    private Integer isHot;

    private Integer regionId;

    private String region;

    private Date createTime;

    private Date sellTime;

    private String guarantee;

    private String unit;

    private String remark;

    private Integer isDelete;

    private Integer sellNum;

    private BigDecimal price;

    private Integer isPromotion;

    private BigDecimal promotionPrice;

    private List<MallGoodsResp> goodsResps;

    private List<MallGoodsGalleryResp> galleryResps;

    private List<MallGoodsAttributeResp> attributeResps;
}