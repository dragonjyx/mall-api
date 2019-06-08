package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypePartsReq {

    private Long id;

    private String name;

    private Long typeId;

    private String typeName;

    private BigDecimal price;
}