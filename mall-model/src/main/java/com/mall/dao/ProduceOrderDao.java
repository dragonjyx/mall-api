package com.mall.dao;

import com.mall.mapper.ProduceOrderMapper;
import com.mall.model.ProduceOrder;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ProduceOrderDao {

    @Autowired
    private ProduceOrderMapper produceOrderMapper;

    public ProduceOrder findByOrderSnAndDeviceTypeId(String orderSn, Long deviceTypeId) {
        ProduceOrder produceOrder = new ProduceOrder();
        produceOrder.setDeviceTypeId(deviceTypeId);
        produceOrder.setOrderSn(orderSn);
        produceOrder = produceOrderMapper.selectOne(produceOrder);
        return produceOrder;
    }

    public int insert(ProduceOrder produceOrder) {
        int count = produceOrderMapper.insert(produceOrder);
        return count;
    }


    public ProduceOrder findByProduceSn(String produceSn) {
        ProduceOrder produceOrder = new ProduceOrder();
        produceOrder.setProduceSn(produceSn);
        produceOrder = produceOrderMapper.selectOne(produceOrder);
        return produceOrder;
    }

    public int updateByProduceSn(ProduceOrder produceOrder) {
        Example example = new Example(ProduceOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("produceSn", produceOrder.getProduceSn());
        int count = produceOrderMapper.updateByExample(produceOrder, example);
        return count;
    }

    public int deleteByProduceSn(String produceSn) {
        Example example = new Example(ProduceOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("produceSn", produceSn);
        int count = produceOrderMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "produceSn", "deviceTypeId", "status", "type"};
        String[] legalLikeWords = { "deviceTypeName", "remark"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<ProduceOrder> pageHandler = new PageHandler<ProduceOrder>(ProduceOrder.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, produceOrderMapper);
        return page;
    }

    public List<ProduceOrder> findByOrderSn(String orderSn) {
        Example example = new Example(ProduceOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        List<ProduceOrder> produceOrders = produceOrderMapper.selectByExample(example);
        return produceOrders;
    }
}
