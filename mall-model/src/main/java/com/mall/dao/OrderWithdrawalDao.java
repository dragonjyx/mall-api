package com.mall.dao;

import com.mall.mapper.OrderWithdrawalMapper;
import com.mall.model.OrderWithdrawal;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class OrderWithdrawalDao {

    @Autowired
    private OrderWithdrawalMapper orderWithdrawalMapper;

    public OrderWithdrawal findByOrderSn(String orderSn) {
        OrderWithdrawal orderWithdrawal = new OrderWithdrawal();
        orderWithdrawal.setOrderSn(orderSn);
        return orderWithdrawalMapper.selectOne(orderWithdrawal);
    }

    public int insert(OrderWithdrawal orderWithdrawal) {
        int count = orderWithdrawalMapper.insert(orderWithdrawal);
        return count;
    }

    public int updateByOrderSn(OrderWithdrawal orderWithdrawal) {
        Example example = new Example(OrderWithdrawal.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderWithdrawal.getOrderSn());
        int count = orderWithdrawalMapper.updateByExample(orderWithdrawal, example);
        return count;
    }

    public int deleteByOrderSn(String orderSn) {
        Example example = new Example(OrderWithdrawal.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = orderWithdrawalMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "applySn", "agentUid", "originStoreId", "newStoreId"};
        String[] legalLikeWords = { "agentName", "originStoreName", "originStoreAddress", "showTime", "reason",
                "newStoreName", "newStoreAddress", "remark"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<OrderWithdrawal> pageHandler = new PageHandler<OrderWithdrawal>(OrderWithdrawal.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderWithdrawalMapper);
        return page;
    }
}
