package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallType {
    private Integer id;

    private String name;

    private Integer sort;

    private Integer categoryId;

    private String categoryName;

    private String status;
}