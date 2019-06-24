package com.mall.dao;

import com.mall.mapper.OrderCommonOffLineMapper;
import com.mall.model.OrderCommon;
import com.mall.model.OrderCommonOffLine;
import com.mall.params.status.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderCommonOffLineDao {

    @Autowired
    private OrderCommonOffLineMapper orderCommonOffLineMapper;



    public int insert(OrderCommonOffLine orderCommon) {
        int count = orderCommonOffLineMapper.insert(orderCommon);
        return count;
    }

    public List<OrderCommonOffLine> findMerchantOrderList(String userId,Integer status){
        List<OrderCommonOffLine> orderCommonOffLines = orderCommonOffLineMapper.findMerchantOrderList(userId,status,0);
        return orderCommonOffLines;
    }


    public List<OrderCommonOffLine> findByMemberIdAndStatus(String memberId, Long status) {
        Example example = new Example(OrderCommonOffLine.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId",memberId);
        if(status != null){
            criteria.andEqualTo("status",status);
        }
        criteria.andEqualTo("isDelete",0);
        example.setOrderByClause("p_create_time DESC");
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineMapper.selectByExample(example);
        return orderCommonOffLineList;
    }

    public OrderCommonOffLine findByOrderSn(String orderSn) {
        Example example = new Example(OrderCommonOffLine.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn).andEqualTo("isDelete",0);
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineMapper.selectByExample(example);
        if(orderCommonOffLineList.isEmpty()){
            return null;
        }
        return orderCommonOffLineList.get(0);
    }

    public int updateByOrderSn(OrderCommonOffLine orderCommonOffLine) {
        Example example = new Example(OrderCommonOffLine.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderCommonOffLine.getOrderSn());
        int result = orderCommonOffLineMapper.updateByExample(orderCommonOffLine, example);
        return result;
    }

    public List<OrderCommonOffLine> findByUserAndStatus(String userId, Long status, int type) {
        Example example = new Example(OrderCommonOffLine.class);
        Example.Criteria criteria = example.createCriteria();
        if(status != null){
            criteria.andEqualTo("status",status);
        }
        criteria.andEqualTo("isDelete",0);
        if(type == UserType.AGENT.value){
            criteria.andEqualTo("regionUserId",userId);
        }else if(type == UserType.DELIVERY.value){
            criteria.andEqualTo("deliverUserId",userId);
        }else if(type == UserType.SUPPLIER.value){
            criteria.andEqualTo("merchantUserId",userId);
        }else{
            log.error("........................................用户类型错误..................................");
            return new ArrayList<OrderCommonOffLine>();
        }
        example.setOrderByClause("p_create_time DESC");
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineMapper.selectByExample(example);
        return orderCommonOffLineList;
    }

    public List<OrderCommonOffLine> findOrderList() {
        Example example = new Example(OrderCommonOffLine.class);
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineMapper.selectByExample(example);
        return orderCommonOffLineList;
    }
}
