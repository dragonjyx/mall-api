package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRank {
    
    private Integer id;

    private String name;

    private Integer minPoints;

    private Integer maxPoints;

    private String discount;

    private Boolean showPirce;

    private Boolean status;

    private Date createTime;

    private String remark;
}