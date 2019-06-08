package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreShowReq {

    private Long id;

    private Long storeId;

    private Integer deviceTypeId;

    private String deviceTypeName;

    private String goodsSn;

    private String goodsCode;

    private String deviceSn;

    private Date showStart;

    private Date showEnd;
}