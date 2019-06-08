package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeReq {

    private Long id;

    private String name;

    private Long deviceCategoryId;

    private String deviceCategoryName;

    private Long factoryId;

    private String factoryName;

    private String model;

    private String remark;

}