package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResp {

    private Long id;

    private String code;

    private String name;

    private String icon;

    private String config;

    private String info;

    private Boolean isOnline;

    private Integer status;

}