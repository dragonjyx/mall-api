package com.mall.dao;

import com.mall.mapper.MallAttributeValueMapper;
import com.mall.model.MallAttributeValue;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class MallAttributeValueDao {

    @Autowired
    private MallAttributeValueMapper mallAttributeValueMapper;


    public MallAttributeValue findByName(String name) {
        MallAttributeValue mallAttributeValue = new MallAttributeValue();
        mallAttributeValue.setName(name);
        mallAttributeValue = mallAttributeValueMapper.selectOne(mallAttributeValue);
        return mallAttributeValue;
    }

    public int insert(MallAttributeValue mallAttributeValue) {
        int count = mallAttributeValueMapper.insert(mallAttributeValue);
        return count;
    }

    public int updateByCode(MallAttributeValue mallAttributeValue) {
        Example example = new Example(MallAttributeValue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", mallAttributeValue.getCode());
        int count = mallAttributeValueMapper.updateByExample(mallAttributeValue, example);
        return count;
    }

    public int deleteByCode(String code) {
        Example example = new Example(MallAttributeValue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        int count = mallAttributeValueMapper.deleteByExample(example);
        return count;
    }

    public int updateMallAttributeValueByAttrId(Long attrId) {
        int count = mallAttributeValueMapper.updateMallAttributeValueByAttrId(attrId);
        return count;
    }

    public int updateMallAttributeValueByTypeId(Long typeId) {
        int count = mallAttributeValueMapper.updateMallAttributeValueByTypeId(typeId);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "code", "typeId", "attrId", "id"};
        String[] legalLikeWords = { "name"};
        String[] legalOrderWords = { "sort", "id"};
        PageHandler<MallAttributeValue> pageHandler = new PageHandler<MallAttributeValue>(MallAttributeValue.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallAttributeValueMapper);
        return page;
    }
}
