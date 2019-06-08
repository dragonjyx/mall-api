package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class FactoryReq {

    private Long id;

    private String name;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String district;

    private String districtName;

    private String address;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}