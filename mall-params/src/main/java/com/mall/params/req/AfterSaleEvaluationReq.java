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
public class AfterSaleEvaluationReq {

    private Long id;

    private Long afterSaleId;

    private String uid;

    private String name;

    private Double score;

    private String msg;

    private Date createTime;

    
}