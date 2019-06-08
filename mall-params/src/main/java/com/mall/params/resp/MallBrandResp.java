package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallBrandResp {
    private Long id;

    private String name;

    private String intial;

    private String logo;

    private String desc;

    private String url;

    private String sort;

    private Long categoryId;

    private Integer isRecommened;

    private String type;

    private String status;
}