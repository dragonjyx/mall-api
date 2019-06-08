package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDevice {
    private Integer id;

    private String deviceId;

    private Integer deviceTypeId;

    private String devTypeName;

    private Integer uid;

    private Date addTime;
}