package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallGoodsGalleryResp {

    private Long id;

    private String goodsSn;

    private String goodsName;

    private String imgUrl;

    private String imgDesc;

    private String thumbUrl;

    private String imgOriginal;
}