package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResp {

    private Long id;

    private String name;

    private String phone;

    private String address;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark;
}