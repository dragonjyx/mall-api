package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSendLogsReq {
    private Integer id;

    private String deviceId;

    private Integer repertoryId;

    private String action;

    private Date actionTime;
}