package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallBrandReq {

    private Long id;

    private String name;

    private String intial;

    private String logo;

    private String brandDesc;

    private String url;

    private String sort;

    private Long categoryId;

    private Integer isRecommened;

    private String type;

    private Integer status;
}