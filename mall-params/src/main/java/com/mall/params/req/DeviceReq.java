package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceReq {

    private Long id;

    private String deviceId;

    private String deviceSn;

    private Long typeId;

    private String typeName;

    private Integer status;

    private Long repertoryId;

    private String repertoryName;

    private Date createTime;

    private Date updateTime;

    private String remark;
}