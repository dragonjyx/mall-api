package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallSpecRsep {
    private Long id;

    private String name;

    private Integer sort;

    private Long categoryId;

    private String categoryName;

    private Integer status;
}