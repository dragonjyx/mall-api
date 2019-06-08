package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeResp {

    private Long id;

    private String name;

    private Long deviceCategoryId;

    private String deviceCategoryName;

    private Long factoryId;

    private String factoryName;

    private String model;

    private String remark;
}