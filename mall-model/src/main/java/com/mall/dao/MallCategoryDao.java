package com.mall.dao;

import com.mall.mapper.MallCategoryMapper;
import com.mall.model.MallCategory;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class MallCategoryDao {

    @Autowired
    private MallCategoryMapper mallCategoryMapper;

    public int findCountById(Long id) {

        Example example = new Example(MallCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        return mallCategoryMapper.selectCountByExample(example);
    }

    public int insert(MallCategory mallCategory) {

        return mallCategoryMapper.insertSelective(mallCategory);
    }

    public MallCategory findByName(String categoryName) {

        MallCategory mallCategory = new MallCategory();
        mallCategory.setCategoryName(categoryName);
        return mallCategoryMapper.selectOne(mallCategory);
    }

    public int updateById(Long id, MallCategory mallCategory) {
        Example example = new Example(MallCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        return mallCategoryMapper.updateByExampleSelective(mallCategory, example);
    }

    public int deleteById(Long id) {
        Example example = new Example(MallCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        return mallCategoryMapper.deleteByExample(example);
    }

    public int deleteByParentId(Long id) {
        Example example = new Example(MallCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", id);
        return mallCategoryMapper.deleteByExample(example);
    }

    public MallCategory findById(Long id) {
        MallCategory mallCategory = new MallCategory();
        mallCategory.setId(id);
        return mallCategoryMapper.selectOne(mallCategory);
    }

    public Page pageMallCategory(Page page) {
        String[] legalKeyWords = { "typeId", "id", "pid", "status"};
        String[] legalLikeWords = { "categoryName", "typeName", "remark", "categoryDesc"};
        String[] legalOrderWords = { "sort", "id"};
        PageHandler<MallCategory> pageHandler = new PageHandler<MallCategory>(MallCategory.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallCategoryMapper);
        return page;
    }

    public int updateMallCategoryByTypeId(Long typeId) {
        int count = mallCategoryMapper.updateMallCategoryByTypeId(typeId);
        return count;
    }
}
