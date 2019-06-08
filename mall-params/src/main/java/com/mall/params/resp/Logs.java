package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logs {
    private Integer id;

    private String action;

    private String uid;

    private String userName;

    private Date createTime;

    private String reamrk;
}