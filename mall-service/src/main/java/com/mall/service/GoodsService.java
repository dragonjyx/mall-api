package com.mall.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mall.model.DistributionFee;
import com.mall.model.MallGoodsCommon;
import com.mall.params.page.PageCondition;

public interface GoodsService {

    PageInfo<MallGoodsCommon> goodsListPage(PageCondition condition,String goodsName,Long typeId,Long schoolDormId);

    JSONObject findDetailByGoodsSn(String sn);

    DistributionFee findbyType(Integer type);
}
