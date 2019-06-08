package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleTemplateResp {

    private Long id;

    private Long deviceTypeId;

    private String errType;

    private String errIcon;

    private String errIconClick;
}