package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendOrderDeviceReq {

    private Long id;

    private String orderSn;

    private String sendSn;

    private Long deviceTypeId;

    private String deviceTypeName;

    private String deviceSn;
}