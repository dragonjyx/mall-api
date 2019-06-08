package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallAttributeResp {
    private Integer id;

    private String name;

    private Long typeId;

    private Integer sort;

    private Integer status;
}