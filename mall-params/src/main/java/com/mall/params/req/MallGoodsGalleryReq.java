package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallGoodsGalleryReq {

    private Long id;

    private String goodsSn;

    private String imgUrl;

    private String imgDesc;

    private String thumbUrl;

    private String imgOriginal;
}