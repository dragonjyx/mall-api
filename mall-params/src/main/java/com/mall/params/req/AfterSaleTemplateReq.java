package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleTemplateReq {

    private Long id;

    private Long deviceTypeId;

    private String errType;

    private String errIcon;

    private String errIconClick;
}