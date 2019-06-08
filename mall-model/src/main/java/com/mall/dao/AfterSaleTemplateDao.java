package com.mall.dao;

import com.mall.mapper.AfterSaleTemplateMapper;
import com.mall.model.AfterSaleTemplate;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class AfterSaleTemplateDao {

    @Autowired
    private AfterSaleTemplateMapper afterSaleTemplateMapper;

    public AfterSaleTemplate findByDeviceTypeIdAndErrType(Long deviceTypeId, String errType) {
        AfterSaleTemplate afterSaleTemplate = new AfterSaleTemplate();
        afterSaleTemplate.setDeviceTypeId(deviceTypeId);
        afterSaleTemplate.setErrType(errType);
        return afterSaleTemplateMapper.selectOne(afterSaleTemplate);
    }

    public int insert(AfterSaleTemplate afterSaleTemplate) {
        int count = afterSaleTemplateMapper.insert(afterSaleTemplate);
        return count;
    }

    public AfterSaleTemplate findById(Long id) {
        AfterSaleTemplate afterSaleTemplate = new AfterSaleTemplate();
        afterSaleTemplate.setId(id);
        return afterSaleTemplateMapper.selectOne(afterSaleTemplate);
    }

    public int updateById(AfterSaleTemplate afterSaleTemplate) {
        Example example = new Example(AfterSaleTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", afterSaleTemplate.getId());
        int count = afterSaleTemplateMapper.updateByExample(afterSaleTemplate, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(AfterSaleTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = afterSaleTemplateMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "deviceTypeId"};
        String[] legalLikeWords = { "errType"};
        String[] legalOrderWords = { "id"};
        PageHandler<AfterSaleTemplate> pageHandler = new PageHandler<AfterSaleTemplate>(AfterSaleTemplate.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, afterSaleTemplateMapper);
        return page;
    }
}
