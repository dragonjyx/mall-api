package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypePartsResp {

    private Long id;

    private String name;

    private Long typeId;

    private String typeName;

    private BigDecimal price;
}