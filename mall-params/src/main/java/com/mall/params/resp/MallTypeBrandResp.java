package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallTypeBrandResp {
    private Long id;

    private Long typeId;

    private Long brandId;

    private String typeName;

    private String brandName;
}