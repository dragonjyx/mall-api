package com.mall.dao;

import com.mall.mapper.MallBrandMapper;
import com.mall.model.MallBrand;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallBrandDao {

    @Autowired
    private MallBrandMapper mallBrandMapper;

    public MallBrand findByName(String name) {
        MallBrand mallBrand = new MallBrand();
        mallBrand.setName(name);
        mallBrand = mallBrandMapper.selectOne(mallBrand);
        return mallBrand;
    }

    public int insert(MallBrand mallBrand) {
        int count = mallBrandMapper.insert(mallBrand);
        return count;
    }

    public int updateById(MallBrand mallBrand) {
        Example example = new Example(MallBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",mallBrand.getId());
        int count = mallBrandMapper.updateByExample(mallBrand,example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(MallBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = mallBrandMapper.deleteByExample(example);
        return count;
    }

    public int updateMallBrandByCategoryId(Long categoryId) {
        int count = mallBrandMapper.updateMallBrandByCategoryId(categoryId);
        return count;
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "intial", "status", "categoryId", "isRecommened"};
        String[] legalLikeWords = { "name", "brandDesc"};
        String[] legalOrderWords = { "sort", "intial", "id"};
        PageHandler<MallBrand> pageHandler = new PageHandler<MallBrand>(MallBrand.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallBrandMapper);
        return page;
    }

    public MallBrand findById(Long id) {
        MallBrand mallBrand = new MallBrand();
        mallBrand.setId(id);
        mallBrand = mallBrandMapper.selectOne(mallBrand);
        return mallBrand;
    }

    public List<MallBrand> findByIds(List<Object> brandIds) {
        Example example = new Example(MallBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", brandIds);
        List<MallBrand> mallBrands = mallBrandMapper.selectByExample(example);
        return mallBrands;
    }
}
