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
public class AfterManApplyReq {

    private Long id;

    private Long afterSaleId;

    private String uid;

    private Date distributionTime;

    private String remark;
}