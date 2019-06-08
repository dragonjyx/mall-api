package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recharge {
    private Integer id;

    private String sn;

    private String uid;

    private String type;

    private String status;

    private String paymentCode;

    private String certificate;

    private Date createTime;

    private String remark;
}