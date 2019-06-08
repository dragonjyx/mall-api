package com.mall.service;


import com.mall.model.MallAddress;
import com.mall.params.page.Page;

import java.util.List;


public interface MallAddressService {

    Integer add(MallAddress mallAddress);

    Integer update(MallAddress mallAddress);

    Integer delete(String addressSn);

    MallAddress findAddressByUidAndAddress(String uid, String addressSn);

    List<MallAddress> findAllAddressByUid(String uid);

    Page pageAddress(Page page);

    int setDefaultAddress(String memberId, String addressSn, Integer isDefault);

    MallAddress findByAddressSn(String addressSn);

    MallAddress findMyDefaultByAddressSn(String memberId);
}
