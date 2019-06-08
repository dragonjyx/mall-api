package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSendLogs {
    private Integer id;

    private String deviceId;

    private Integer repertoryId;

    private String action;

    private Date actionTime;
}