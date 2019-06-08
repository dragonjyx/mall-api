package com.mall.dao;

import com.mall.mapper.SendOrderMapper;
import com.mall.model.SendOrder;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SendOrderDao {

    @Autowired
    private SendOrderMapper sendOrderMapper;

    public List<SendOrder> findByOrderSn(String orderSn) {
        Example example = new Example(SendOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        List<SendOrder> sendOrders = sendOrderMapper.selectByExample(example);
        return sendOrders;
    }

    public int insert(SendOrder sendOrder) {
        int count = sendOrderMapper.insert(sendOrder);
        return count;
    }

    public SendOrder findBySendSn(String sendSn) {
        SendOrder sendOrder = new SendOrder();
        sendOrder.setSendSn(sendSn);
        return sendOrderMapper.selectOne(sendOrder);
    }

    public int updateBySendSn(SendOrder sendOrder) {
        Example example = new Example(SendOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sendSn", sendOrder.getSendSn());
        int count = sendOrderMapper.updateByExample(sendOrder, example);
        return count;
    }

    public int deleteByOrderSn(String orderSn) {
        Example example = new Example(SendOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        int count = sendOrderMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "orderSn", "sendSn", "uid", "phone", "status"};
        String[] legalLikeWords = { "sender", "province", "city", "district", "address", "remark"};
        String[] legalOrderWords = { "createTime", "updateTime", "sendTime", "finishedTime"};
        PageHandler<SendOrder> pageHandler = new PageHandler<SendOrder>(SendOrder.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, sendOrderMapper);
        return page;
    }
}
