package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepertoryDeviceTypeReq {

    private Long id;

    private Long repertoryId;

    private Long typeId;

    private Long typeName;

    private Integer num;

    private Integer warn;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String remark;
}