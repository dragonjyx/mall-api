package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Integer id;

    private String name;

    private String code;

    private Integer type;

    private Date createTime;

    private Date updateTime;
}