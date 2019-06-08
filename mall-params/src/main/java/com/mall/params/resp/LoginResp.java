package com.mall.params.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResp implements Serializable {

    private String userId;
    private String openId;
    private String userName;
    private Integer result;

}