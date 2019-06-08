package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLogs {
    private Integer id;

    private String orderSn;

    private String handlerUid;

    private String msg;

    private String handleNote;

    private Date logTime;
}