package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallGoodsCommonReq {

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

    private List<MallGoodsReq> mallGoodsReqs;

    private List<MallGoodsGalleryReq> mallGoodsGalleryReqs;

    private List<MallGoodsAttributeReq> mallGoodsAttributeReqs;

    private Integer permissionCode;

    private Integer isDelete;

    private Integer sellNum;
}