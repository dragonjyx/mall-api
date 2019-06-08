package com.mall.params.resp;

import lombok.Data;

import java.util.Date;

@Data
public class StoreUserResp {

    private Long id;

    private Long storeId;

    private String uid;

    private String userName;

    private String job;

    private Date createTime;

}
