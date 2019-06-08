package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleEvaluationResp {

    private Integer id;

    private String afterSaleId;

    private String uid;

    private String name;

    private Double score;

    private String msg;

    private Date createTime;

    
}