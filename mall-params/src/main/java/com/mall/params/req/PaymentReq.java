package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReq {

    private Long id;

    private String code;

    private String name;

    private String icon;

    private String info;

    private String config;

    private Boolean isOnline;

    private Integer status;
}