package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallAttributeValueReq {
    private Long id;

    private String name;

    private String code;

    private Long attrId;

    private Long typeId;

    private String sort;
}