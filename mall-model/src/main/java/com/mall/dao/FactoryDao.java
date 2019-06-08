package com.mall.dao;

import com.mall.mapper.FactoryMapper;
import com.mall.model.Factory;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class FactoryDao {

    @Autowired
    private FactoryMapper factoryMapper;

    public int insert(Factory factory) {

        int count = factoryMapper.insert(factory);
        return count;
    }

    public Factory findById(Long id) {
        Factory factory = new Factory();
        factory.setId(id);
        return factoryMapper.selectOne(factory);
    }

    public int updateById(Factory factory) {
        Example example = new Example(Factory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", factory.getId());
        int count = factoryMapper.updateByExample(factory, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(Factory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = factoryMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "status", "province", "city", "district"};
        String[] legalLikeWords = { "name", "address", "provinceName", "cityName", "districtName", "remark"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<Factory> pageHandler = new PageHandler<Factory>(Factory.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, factoryMapper);
        return page;
    }
}
