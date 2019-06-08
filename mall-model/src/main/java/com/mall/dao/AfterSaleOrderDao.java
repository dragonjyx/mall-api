package com.mall.dao;

import com.mall.mapper.AfterSaleOrderMapper;
import com.mall.model.AfterSaleOrder;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class AfterSaleOrderDao {

    @Autowired
    private AfterSaleOrderMapper afterSaleOrderMapper;

    public int insert(AfterSaleOrder afterSaleOrder) {
        int count = afterSaleOrderMapper.insert(afterSaleOrder);
        return count;
    }

    public AfterSaleOrder findByAfterSaleSn(String afterSaleSn) {
        AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
        afterSaleOrder.setAfterSaleSn(afterSaleSn);
        return afterSaleOrderMapper.selectOne(afterSaleOrder);
    }

    public int updateByAfterSaleSn(AfterSaleOrder afterSaleOrder) {
        Example example = new Example(AfterSaleOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("afterSaleSn", afterSaleOrder.getAfterSaleSn());
        int count = afterSaleOrderMapper.updateByExample(afterSaleOrder, example);
        return count;
    }

    public int deleteByAfterSaleSn(String afterSaleSn) {
        Example example = new Example(AfterSaleOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("afterSaleSn", afterSaleSn);
        int count = afterSaleOrderMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "afterSaleSn", "uid", "afterApplyId", "amount", "paymentCode", "status"};
        String[] legalLikeWords = { "userName", "remark"};
        String[] legalOrderWords = { "id", "createTime", "payTime"};
        PageHandler<AfterSaleOrder> pageHandler = new PageHandler<AfterSaleOrder>(AfterSaleOrder.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, afterSaleOrderMapper);
        return page;
    }
}
