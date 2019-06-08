package com.mall.dao;

import com.mall.mapper.OrderGoodsDeviceMapper;
import com.mall.model.OrderGoodsDevice;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OrderGoodsDeviceDao {

    @Autowired
    private OrderGoodsDeviceMapper orderGoodsDeviceMapper;

    public OrderGoodsDevice findByOrderSnAndDeviceSn(String orderSn, String deviceSn) {

        OrderGoodsDevice orderGoodsDevice = new OrderGoodsDevice();
        orderGoodsDevice.setDeviceSn(deviceSn);
        orderGoodsDevice.setOrderSn(orderSn);
        return orderGoodsDeviceMapper.selectOne(orderGoodsDevice);
    }

    public int insert(OrderGoodsDevice orderGoodsDevice) {
        int count = orderGoodsDeviceMapper.insert(orderGoodsDevice);
        return count;
    }

    public OrderGoodsDevice findById(Long id) {
        OrderGoodsDevice orderGoodsDevice = new OrderGoodsDevice();
        orderGoodsDevice.setId(id);
        return orderGoodsDeviceMapper.selectOne(orderGoodsDevice);
    }

    public int deleteByOrderSn(String orderSn) {
        Example example = new Example(OrderGoodsDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = orderGoodsDeviceMapper.deleteByExample(example);
        return count;
    }

    public List<OrderGoodsDevice> findByOrder(String orderSn) {
        Example example = new Example(OrderGoodsDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        List<OrderGoodsDevice> orderGoodsDevices = orderGoodsDeviceMapper.selectByExample(example);
        return orderGoodsDevices;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "goodsSn", "deviceSn", "agentUid"};
        String[] legalOrderWords = { "id", "guaranteeTime"};
        PageHandler<OrderGoodsDevice> pageHandler = new PageHandler<OrderGoodsDevice>(OrderGoodsDevice.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderGoodsDeviceMapper);
        return page;
    }

    public OrderGoodsDevice findByDeviceSn(String deviceSn){
        Example example = new Example(OrderGoodsDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceSn", deviceSn);
        List<OrderGoodsDevice> orderGoodsDevices = orderGoodsDeviceMapper.selectByExample(example);
        if(orderGoodsDevices.isEmpty()){
            return null;
        }
        return orderGoodsDevices.get(0);
    }



}
