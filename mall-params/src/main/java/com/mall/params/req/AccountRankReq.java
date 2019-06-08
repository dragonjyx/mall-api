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
public class AccountRankReq {
    
    private Integer id;

    private String name;

    private Integer minPoints;

    private Integer maxPoints;

    private String discount;

    private Integer showPirce;

    private Date createTime;

    private String remark;
}