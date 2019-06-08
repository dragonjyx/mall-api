package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallAddress {
    private Integer id;

    private String uid;

    private String consignee;

    private String country;

    private String province;

    private String city;

    private String district;

    private String address;

    private String zipcode;

    private String phone;

    private Byte isDefault;
}