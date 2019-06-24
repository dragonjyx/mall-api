package com.mall.dao;

import com.mall.mapper.OrderCommonMapper;
import com.mall.model.OrderCommon;
import com.mall.model.OrderCommonOffLine;
import com.mall.params.status.RefundStatus;
import com.mall.params.status.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderCommonDao {

    @Autowired
    private OrderCommonMapper orderCommonMapper;

    public int insert(OrderCommon orderCommon) {
        int count = orderCommonMapper.insert(orderCommon);
        return count;
    }

    public List<OrderCommon> findMerchantOrderList(String userId, Integer status){
        List<OrderCommon> orderCommonList = orderCommonMapper.findMerchantOrderList(userId,status,0);
        return orderCommonList;
    }

    public int updateByOrderSn(OrderCommon orderCommon) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderCommon.getOrderSn());
        int result = orderCommonMapper.updateByExample(orderCommon, example);
        return result;
    }

    public int updateSelectiveByOrderSn(OrderCommon orderCommon) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderCommon.getOrderSn());
        int count = orderCommonMapper.updateByExampleSelective(orderCommon, example);
        return count;
    }

    public OrderCommon findByOrderSn(String orderSn) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn).andEqualTo("isDelete",0);
        List<OrderCommon> orderCommonList = orderCommonMapper.selectByExample(example);
        if(orderCommonList.isEmpty()){
            return null;
        }
        return orderCommonList.get(0);
    }

    public List<OrderCommon> findByMemberIdAndStatus(String memberId, Long status) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId",memberId);
        if(status != null){
            criteria.andEqualTo("status",status);
        }
        criteria.andEqualTo("isDelete",0);
        example.setOrderByClause("p_create_time DESC");
        List<OrderCommon> orderCommonList = orderCommonMapper.selectByExample(example);
        return orderCommonList;
    }


    /**
     * @param userId
     * @param status
     * @param type  2:代理商 3:配送员 4:供货商
     * @return
     */
    public List<OrderCommon> findByUserAndStatus(String userId, Long status, int type) {
        Example example = new Example(OrderCommon.class);
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
            return new ArrayList<OrderCommon>();
        }
        example.setOrderByClause("p_create_time DESC");
        List<OrderCommon> orderCommonList = orderCommonMapper.selectByExample(example);
        return orderCommonList;
    }

    public OrderCommon findByTradeNo(String out_trade_no) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tradeNo", out_trade_no);
        List<OrderCommon> orderCommonList = orderCommonMapper.selectByExample(example);
        if(orderCommonList.isEmpty()){
            return null;
        }
        return orderCommonList.get(0);
    }

    public List<OrderCommon> findTimeOutNoPayOrders(Date timeOutDate, int status) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", status)
                .andLessThan("createTime",timeOutDate);
        List<OrderCommon> orderCommonList = orderCommonMapper.selectByExample(example);
        return orderCommonList;
    }

    public List<OrderCommon> findShareOrdersAndPayFinish(Date day, List<Object> statusList) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("status",statusList)
                .andEqualTo("refundStatus",RefundStatus.NORMAL.value)//没有退款
                .andLessThan("payTime",day);
        List<OrderCommon> orderCommonList = orderCommonMapper.selectByExample(example);
        return orderCommonList;
    }
}
