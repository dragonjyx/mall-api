package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallSpecReq {

    private Long id;

    private String name;

    private Integer sort;

    private Long categoryId;

    private String categoryName;

    private Integer status;
}