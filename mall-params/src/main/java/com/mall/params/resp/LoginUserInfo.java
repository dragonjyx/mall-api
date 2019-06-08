package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo implements Serializable {

    private String userName;

    private String nickName;

    private String email;

    private String phone;

    private String avatarUrl;

    private Integer sex;

    private String address;

    private String birthday;

    private Integer source;

    private String countryCode;

    private String loginIp;

}
