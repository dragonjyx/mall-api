package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallAttributeReq {
    private Long id;

    private String name;

    private Long typeId;

    private Integer sort;

    private Integer status;
}