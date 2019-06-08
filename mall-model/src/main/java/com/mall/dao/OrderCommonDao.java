package com.mall.dao;

import com.mall.mapper.OrderCommonMapper;
import com.mall.model.OrderCommon;
import com.mall.model.OrderCommonOffLine;
import com.mall.params.status.RefundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

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


    public List<OrderCommon> findByUserAndStatus(List<Object> dormIdList, Long status) {
        Example example = new Example(OrderCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",status).andIn("dormId",dormIdList);
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
