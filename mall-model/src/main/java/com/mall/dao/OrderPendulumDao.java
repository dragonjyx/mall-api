package com.mall.dao;

import com.mall.mapper.OrderPendulumMapper;
import com.mall.model.OrderPendulum;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class OrderPendulumDao {

    @Autowired
    private OrderPendulumMapper orderPendulumMapper;

    public OrderPendulum findByOrderSn(String orderSn) {

        OrderPendulum orderPendulum = new OrderPendulum();
        orderPendulum.setOrderSn(orderSn);
        return orderPendulumMapper.selectOne(orderPendulum);
    }

    public int insert(OrderPendulum orderPendulum) {
        int count = orderPendulumMapper.insert(orderPendulum);
        return count;
    }

    public int updateByOrderSn(OrderPendulum orderPendulum) {

        Example example = new Example(OrderPendulum.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderPendulum.getOrderSn());
        int count = orderPendulumMapper.updateByExample(orderPendulum, example);
        return count;
    }

    public int deleteByOrderSn(String orderSn) {
        Example example = new Example(OrderPendulum.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = orderPendulumMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "applySn", "contractSn", "agentUid", "storeId"};
        String[] legalLikeWords = { "remark", "agentName", "storeName", "storeAddress", "assistant1", "phone1",
                "assistant2", "phone2", "province", "city", "district"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<OrderPendulum> pageHandler = new PageHandler<OrderPendulum>(OrderPendulum.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderPendulumMapper);
        return page;
    }
}
