package com.mall.dao;

import com.mall.mapper.AfterSaleOrderPartsMapper;
import com.mall.model.AfterSaleOrderParts;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AfterSaleOrderPartsDao {

    @Autowired
    private AfterSaleOrderPartsMapper orderPartsMapper;

    public int insert(AfterSaleOrderParts parts) {
        int count = orderPartsMapper.insert(parts);
        return count;
    }

    public List<AfterSaleOrderParts> findByAfterSaleSn(String afterSaleSn) {
        Example example = new Example(AfterSaleOrderParts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("afterSaleOrderSn", afterSaleSn);
        List<AfterSaleOrderParts> afterSaleOrderParts = orderPartsMapper.selectByExample(example);
        return afterSaleOrderParts;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "afterSaleOrderSn", "price", "partsId", "num"};
        String[] legalLikeWords = { "partsName"};
        String[] legalOrderWords = { "id", "price", "num"};
        PageHandler<AfterSaleOrderParts> pageHandler = new PageHandler<AfterSaleOrderParts>(AfterSaleOrderParts.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, orderPartsMapper);
        return page;
    }
}
