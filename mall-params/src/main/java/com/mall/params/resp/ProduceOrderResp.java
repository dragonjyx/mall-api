package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduceOrderResp {
    private Integer id;

    private String produceSn;

    private String orderSn;

    private Long deviceTypeId;

    private String deviceTypeName;

    private Integer num;

    private String status;

    private String type;

    private Date createTime;

    private Date finishedTime;

    private String remark;
}