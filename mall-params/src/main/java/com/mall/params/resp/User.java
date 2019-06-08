package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String uid;

    private Integer roleId;

    private String roleName;

    private String username;

    private String password;

    private String alias;

    private Date createTime;

    private Date loginTime;

    private String loginIp;

    private Long visitCount;

    private Integer errorCount;

    private String isAccount;

    private Integer userinfoId;
}