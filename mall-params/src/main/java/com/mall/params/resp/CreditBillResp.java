package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
public class CreditBillResp {

    private Integer id;
    private String phone;
    private String email;
    private String uid;
    private String name;
    private String adjustUid;
    private String adjustUserName;
    private Integer type;
    private String orderSn;
    private BigDecimal amount;//调整额度
    private Date createTime;

}
