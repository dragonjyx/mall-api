package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddEquipment{

    private Long id;
    private String deviceSn;//设备编号
    private String deviceName;//设备名称
    private Date guaranteeTime;//保修期
    private String goodsImage;//商品图片
    private String goodsSn;//商品sn
    private String goodsCode;//商品代码


}



