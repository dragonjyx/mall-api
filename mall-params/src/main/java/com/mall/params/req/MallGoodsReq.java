package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallGoodsReq {

    private Long id;

    private String goodsSn;

    private String code;

    private String name;

    private String goodsImage;

    private String barCode;

    private String specIds;

    private String specCodes;

    private String specValues;

    private BigDecimal marketPrice;

    private BigDecimal price;

    private Integer isPromotion;

    private BigDecimal promotionPrice;

    private String promotionType;

    private Date promotionStart;

    private Date promotionEnd;

    private Integer limitNum;

    private Date createTime;

    private Date updateTime;

    private Integer permissionCode;

    private String remark;

    private Integer stockNum;

    private Integer isDelete;

    private Integer sellNum;

    private Long deviceTypeId;

    private String deviceTypeName;

    private Long factoryId;

    private String factoryName;
}