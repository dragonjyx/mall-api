package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallAttributeValueResp {
    private Integer id;

    private String name;

    private String code;

    private Integer attrId;

    private Integer typeId;

    private String sort;
}