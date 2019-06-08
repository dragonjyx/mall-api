package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer id;

    private String name;

    private String permissionCode;

    private String desc;

    private Date createTime;

    private Date updateTime;

    private String remark;
}