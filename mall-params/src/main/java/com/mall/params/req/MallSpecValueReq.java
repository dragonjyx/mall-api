package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallSpecValueReq {
    private Long id;

    private String code;

    private String name;

    private Long specId;

    private Long categoryId;

    private Integer sort;
}