package com.mall.dao;

import com.mall.mapper.OrderChoiceMapper;
import com.mall.model.OrderChoice;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class OrderChoiceDao {

    @Autowired
    private OrderChoiceMapper orderChoiceMapper;

    public OrderChoice findByOrderSn(String orderSn) {
        OrderChoice orderChoice = new OrderChoice();
        orderChoice.setOrderSn(orderSn);
        return orderChoiceMapper.selectOne(orderChoice);
    }

    public int insert(OrderChoice orderChoice) {
        int count = orderChoiceMapper.insert(orderChoice);
        return count;
    }

    public int deleteByOrderSn(String orderSn) {

        Example example = new Example(OrderChoice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = orderChoiceMapper.deleteByExample(example);
        return count;
    }

    public int updateByOrderSn(OrderChoice orderChoice) {

        Example example = new Example(OrderChoice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderChoice.getOrderSn());
        int count = orderChoiceMapper.updateByExample(orderChoice, example);
        return count;
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "orderSn", "applySn", "contractSn", "agentUid", "phone", "repertoryId",
                "choiceType"};
        String[] legalLikeWords = { "remark", "userName", "repertoryName", "province", "city", "district", "address"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<OrderChoice> pageHandler = new PageHandler<OrderChoice>(OrderChoice.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderChoiceMapper);
        return page;
    }
}
