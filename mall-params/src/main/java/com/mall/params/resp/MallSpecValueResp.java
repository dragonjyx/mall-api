package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallSpecValueResp {
    private Integer id;

    private String code;

    private String name;

    private Long specId;

    private Long categoryId;

    private Integer sort;
}