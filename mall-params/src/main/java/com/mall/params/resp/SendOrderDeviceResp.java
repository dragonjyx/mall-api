package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendOrderDeviceResp {

    private Long id;

    private String orderSn;

    private String sendSn;

    private Long deviceTypeId;

    private String deviceTypeName;

    private String deviceSn;
}