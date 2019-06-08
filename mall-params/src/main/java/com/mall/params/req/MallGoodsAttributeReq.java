package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallGoodsAttributeReq {

    private Long id;

    private String goodsSn;

    private Long categoryId;

    private Long typeId;

    private Long attrId;

    private String attrName;

    private Long attrValueId;

    private String attrValueName;
}