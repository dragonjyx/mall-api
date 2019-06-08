package com.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.dao.*;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private MallGoodsCommonDao mallGoodsCommonDao;

    @Autowired
    private MallGoodsDao mallGoodsDao;

    @Autowired
    private MallGoodsAttributeDao mallGoodsAttributeDao;

    @Autowired
    private MallGoodsGalleryDao mallGoodsGalleryDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private DistributionFeeDao distributionFeeDao;


    @Override
    public PageInfo<MallGoodsCommon> goodsListPage(PageCondition condition, String goodsName, Long typeId,Long schoolDormId) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<MallGoodsCommon> mallGoodsCommonList = mallGoodsCommonDao.findMallGoodsCommonByName(goodsName,typeId,schoolDormId);

        for(MallGoodsCommon mallGoodsCommon:mallGoodsCommonList){
            String sn = mallGoodsCommon.getSn();
            List<MallGoods> mallGoodsList = mallGoodsDao.findByGoodsSn(sn);
            mallGoodsCommon.setMallGoodsList(mallGoodsList);
        }

        PageInfo<MallGoodsCommon> userInfoPageInfo = new PageInfo<MallGoodsCommon>(mallGoodsCommonList);
        return userInfoPageInfo;
    }


    @Override
    public JSONObject findDetailByGoodsSn(String sn) {
        MallGoodsCommon mallGoodsCommon = mallGoodsCommonDao.findBySn(sn);
        if(mallGoodsCommon == null){
            log.error("---------- Goods common is null--------");
            return null;
        }

        List<MallGoods> mallGoodsList = mallGoodsDao.findByGoodsSn(sn);
        if(mallGoodsList.isEmpty()){
            log.error("---------- Goods is null--------");
            return null;
        }

        List<MallGoodsAttribute> mallGoodsAttributeList = mallGoodsAttributeDao.findByGoodsSn(sn);
        if(mallGoodsAttributeList.isEmpty()){
            log.error("---------- GoodsAttribute is empty--------");
            return null;
        }


        List<MallGoodsGallery>  mallGoodsGalleryList = mallGoodsGalleryDao.findByGoodsSn(sn);
        if(mallGoodsAttributeList.isEmpty()){
            log.error("---------- GoodsGallery is empty--------");
            return null;
        }


        JSONObject result = new JSONObject();
        result.put("goodsCommon",mallGoodsCommon);
        result.put("goodsList",mallGoodsList);
        result.put("goodsAttribute",mallGoodsAttributeList);
        result.put("goodsGallery",mallGoodsGalleryList);

        return result;
    }

    @Override
    public DistributionFee findbyType(Integer type) {
        DistributionFee distributionFee = distributionFeeDao.findBySchoolType(type);
        return distributionFee;
    }
}
