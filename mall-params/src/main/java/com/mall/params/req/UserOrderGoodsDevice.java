package com.mall.params.req;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderGoodsDevice {
    private String memberId;
    private String userId;
    private String goodsSn;
    private String goodsCode;
    private String deviceSn;
}
