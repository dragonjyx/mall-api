package com.mall.dao;

import com.mall.mapper.OrderExchangeMapper;
import com.mall.model.OrderExchange;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class OrderExchangeDao {

    @Autowired
    private OrderExchangeMapper orderExchangeMapper;

    public OrderExchange findByOrderSn(String orderSn) {
        OrderExchange orderExchange = new OrderExchange();
        orderExchange.setOrderSn(orderSn);
        return orderExchangeMapper.selectOne(orderExchange);
    }

    public int insert(OrderExchange orderExchange) {
        int count = orderExchangeMapper.insert(orderExchange);
        return count;
    }

    public int updateById(OrderExchange exchange) {
        Example example = new Example(OrderExchange.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", exchange.getOrderSn());
        int count = orderExchangeMapper.updateByExample(exchange, example);
        return count;
    }

    public int deleteByOrderSn(String orderSn) {
        Example example = new Example(OrderExchange.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = orderExchangeMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "applySn", "agentUid", "storeId"};
        String[] legalLikeWords = { "agentName", "storeName", "applyInfo", "reason", "remark"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<OrderExchange> pageHandler = new PageHandler<OrderExchange>(OrderExchange.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderExchangeMapper);
        return page;
    }
}
