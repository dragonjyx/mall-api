package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private Integer id;

    private String name;

    private String idCard;

    private String birthday;

    private String email;

    private String partment;

    private String post;

    private String phone;

    private String address;

    private Date createTime;

    private Date updateTime;

    private String remark;
}