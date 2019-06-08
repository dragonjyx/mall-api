package com.mall.dao;

import com.mall.mapper.MallAttributeMapper;
import com.mall.model.MallAttribute;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class MallAttributeDao {

    @Autowired
    private MallAttributeMapper mallAttributeMapper;

    public MallAttribute findByName(String name) {

        MallAttribute mallAttribute = new MallAttribute();
        mallAttribute.setName(name);
        mallAttribute = mallAttributeMapper.selectOne(mallAttribute);
        return mallAttribute;
    }

    public int insert(MallAttribute mallAttribute) {

        return mallAttributeMapper.insertSelective(mallAttribute);
    }

    public int updateById(MallAttribute mallAttribute) {
        Example example = new Example(MallAttribute.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", mallAttribute.getId());
        int count = mallAttributeMapper.updateByExampleSelective(mallAttribute, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(MallAttribute.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = mallAttributeMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "typeId", "status"};
        String[] legalLikeWords = { "name"};
        String[] legalOrderWords = { "id", "sort"};
        PageHandler<MallAttribute> pageHandler = new PageHandler<MallAttribute>(MallAttribute.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallAttributeMapper);
        return page;
    }

    public int updateMallAttributeByTypeId(Long typeId) {
        int count = mallAttributeMapper.updateMallAttributeByTypeId(typeId);
        return count;
    }

    public MallAttribute findById(Long id) {
        MallAttribute mallAttribute = new MallAttribute();
        mallAttribute.setId(id);
        mallAttribute = mallAttributeMapper.selectOne(mallAttribute);
        return mallAttribute;
    }
}
