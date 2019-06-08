package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReq {

    private Long id;

    private String name;

    private String phone;

    private String address;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark;
}