package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBillReq {

    private Integer id;
    private String phone;
    private String email;
    private String name;
    private Date updateTime;
    private Integer type;
    private Integer status;
    private BigDecimal amount;
    private String orderSn;
    private String uid;
}

