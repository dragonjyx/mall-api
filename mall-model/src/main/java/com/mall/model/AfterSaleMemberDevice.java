package com.mall.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_after_sale_member_device")
public class AfterSaleMemberDevice {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_member_id")
    private String memberId;

    @Column(name="p_device_sn")
    private String deviceSn;

    @Column(name="p_goods_sn")
    private String goodsSn;

    @Column(name="p_goods_code")
    private String goodsCode;

}
