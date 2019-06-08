package com.mall.service;

import com.mall.model.MallCart;

import java.util.List;

public interface MallCartService {

    int addMallCart(MallCart mallCart);

    List<MallCart> findMallCatByUid(String uid);

    int deleteCart(long id);

    int deleteAllCart(String memberId);

    int setCartGoodsNum(Long id, Integer number);
}
