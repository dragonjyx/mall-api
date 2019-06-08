package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterManApplyResp {

    private Long id;

    private Long afterSaleId;

    private String uid;

    private Date distributionTime;

    private String remark;
}