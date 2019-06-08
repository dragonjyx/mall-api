package com.mall.dao;

import com.mall.mapper.MallSpecValueMapper;
import com.mall.model.MallSpecValue;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class MallSpecValueDao {

    @Autowired
    private MallSpecValueMapper mallSpecValueMapper;

    public MallSpecValue findByName(String name) {
        MallSpecValue mallSpecValue = new MallSpecValue();
        mallSpecValue.setName(name);
        mallSpecValue = mallSpecValueMapper.selectOne(mallSpecValue);
        return mallSpecValue;
    }

    public int insert(MallSpecValue mallSpecValue) {
        int count = mallSpecValueMapper.insert(mallSpecValue);
        return count;
    }

    public MallSpecValue findByCode(String code) {
        MallSpecValue mallSpecValue = new MallSpecValue();
        mallSpecValue.setCode(code);
        mallSpecValue = mallSpecValueMapper.selectOne(mallSpecValue);
        return mallSpecValue;
    }

    public int updateById(MallSpecValue mallSpecValue) {
        Example example = new Example(MallSpecValue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", mallSpecValue.getId());
        int count = mallSpecValueMapper.updateByExample(mallSpecValue, example);
        return count;
    }

    public int updateMallSpecValueBySpecId(Long specId) {
        int count = mallSpecValueMapper.updateMallSpecValueBySpecId(specId);
        return count;
    }

    public int updateMallSpecValueByCategoryId(Long categoryId) {
        int count = mallSpecValueMapper.updateMallSpecValueByCategoryId(categoryId);
        return count;
    }

    public int deleteMallSpecValueByCode(String code) {
        Example example = new Example(MallSpecValue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        int count = mallSpecValueMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "code", "specId", "categoryId"};
        String[] legalLikeWords = { "name"};
        String[] legalOrderWords = { "sort", "id"};
        PageHandler<MallSpecValue> pageHandler = new PageHandler<MallSpecValue>(MallSpecValue.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallSpecValueMapper);
        return page;
    }
}
