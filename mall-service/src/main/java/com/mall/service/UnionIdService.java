package com.mall.service;

import com.mall.model.UnionIds;

public interface UnionIdService {

    int bindUnionIdAndOpenId(String unionId,String openId);

    UnionIds findByOpenId(String openId);


}
