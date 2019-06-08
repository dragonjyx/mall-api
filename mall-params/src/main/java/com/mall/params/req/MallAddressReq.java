package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallAddressReq {

    private Integer id;

    private String addressSn;

    private String uid;

    private String consignee;

    private String country;

    private String province;

    private String city;

    private String district;

    private String address;

    private String schoolName;

    private String dormName;

    private String zipcode;

    private String phone;

    private Integer isDefault;
}