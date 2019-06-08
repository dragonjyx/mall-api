package com.mall.dao;

import com.mall.mapper.OrderSendMapper;
import com.mall.model.OrderSend;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class OrderSendDao {

    @Autowired
    private OrderSendMapper orderSendMapper;

    public OrderSend findByOrderSn(String orderSn) {
        OrderSend orderSend = new OrderSend();
        orderSend.setOrderSn(orderSn);
        return orderSendMapper.selectOne(orderSend);
    }

    public int insert(OrderSend orderSend) {
        int count = orderSendMapper.insert(orderSend);
        return count;
    }

    public int updateByOrderSn(OrderSend orderSend) {
        Example example = new Example(OrderSend.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSend.getOrderSn());
        int count = orderSendMapper.updateByExample(orderSend, example);
        return count;
    }

    public int deleteByOrderSn(String orderSn) {
        Example example = new Example(OrderSend.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = orderSendMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "applySn", "contractSn", "agentUid", "regionId", "storeId",
                "assistantUid", "phone", "zipcode", "mobile"};
        String[] legalLikeWords = { "region", "company", "storeName", "assistant", "channel", "consignee",
                "country", "province", "city", "district", "address"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<OrderSend> pageHandler = new PageHandler<OrderSend>(OrderSend.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderSendMapper);
        return page;
    }
}
