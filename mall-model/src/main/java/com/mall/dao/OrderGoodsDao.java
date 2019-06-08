package com.mall.dao;

import com.mall.mapper.OrderGoodsMapper;
import com.mall.model.OrderCommon;
import com.mall.model.OrderGoods;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import com.mall.params.req.UserOrderGoodsDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OrderGoodsDao {

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    public OrderGoods findByOrderSnAndGoodsCode(String orderSn, String goodsCode) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderSn(orderSn);
        orderGoods.setGoodsCode(goodsCode);
        return orderGoodsMapper.selectOne(orderGoods);
    }

    public int insert(OrderGoods orderGoods) {
        int result = orderGoodsMapper.insert(orderGoods);
        return result;
    }

    public int updateById(OrderGoods orderGoods) {
        Example example = new Example(OrderGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderGoods.getId());
        int count = orderGoodsMapper.updateByExample(orderGoods, example);
        return count;
    }

    public int deleteByOrderSn(String orderSn) {
        Example example = new Example(OrderGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = orderGoodsMapper.deleteByExample(example);
        return count;
    }

    public List<OrderGoods> findByOrderSn(String orderSn) {
        Example example = new Example(OrderGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(example);
        return orderGoods;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "goodsSn", "goodsCode", "num", "shippingType", "shippingNum", "shipedNum", "isChoice", "choiceNumber"};
        String[] legalLikeWords = { "goodsName", "specValue"};
        String[] legalOrderWords = { "marketPrice", "price", "id"};
        PageHandler<OrderGoods> pageHandler = new PageHandler<OrderGoods>(OrderGoods.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderGoodsMapper);
        return page;
    }

    public UserOrderGoodsDevice findByDeviceSnAndGoods(String deviceSn, String goodsSn, String goodsCode){
        return orderGoodsMapper.findByDeviceSnAndGoods(deviceSn,goodsSn,goodsCode);
    }


}
