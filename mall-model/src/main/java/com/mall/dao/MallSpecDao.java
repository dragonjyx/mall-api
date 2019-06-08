package com.mall.dao;

import com.mall.mapper.MallSpecMapper;
import com.mall.model.MallSpec;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallSpecDao {

    @Autowired
    private MallSpecMapper mallSpecMapper;

    public MallSpec findByName(String name) {
        MallSpec mallSpec = new MallSpec();
        mallSpec.setName(name);
        mallSpec = mallSpecMapper.selectOne(mallSpec);
        return mallSpec;
    }

    public int insert(MallSpec mallSpec) {
        int count = mallSpecMapper.insert(mallSpec);
        return count;
    }

    public int updateById(MallSpec mallSpec) {
        Example example = new Example(MallSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", mallSpec.getId());
        int count = mallSpecMapper.updateByExample(mallSpec, example);
        return count;
    }

    public int deleteMallSpecById(Long id) {
        Example example = new Example(MallSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = mallSpecMapper.deleteByExample(example);
        return count;
    }

    public int updateMallSpecByCategoryId(Long categoryId) {
        int count = mallSpecMapper.updateMallSpecByCategoryId(categoryId);
        return 0;
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "categoryId", "status"};
        String[] legalLikeWords = { "name", "categoryName"};
        String[] legalOrderWords = { "sort", "id"};
        PageHandler<MallSpec> pageHandler = new PageHandler<MallSpec>(MallSpec.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallSpecMapper);
        return page;
    }

    public MallSpec findById(Long id) {
        MallSpec mallSpec = new MallSpec();
        mallSpec.setId(id);
        mallSpec = mallSpecMapper.selectOne(mallSpec);
        return mallSpec;
    }

    public List<MallSpec> findByIds(List<Object> specIds) {
        Example example = new Example(MallSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", specIds);
        List<MallSpec> mallSpecs = mallSpecMapper.selectByExample(example);
        return mallSpecs;
    }
}
