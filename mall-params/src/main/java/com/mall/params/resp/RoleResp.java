package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResp {
    private Long id;

    private String name;

    private String permissionCode;

    private String desc;

    private Date createTime;

    private Date updateTime;

    private String remark;
}