package com.mall.dao;

import com.mall.mapper.MallTypeBrandMapper;
import com.mall.model.MallTypeBrand;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class MallTypeBrandDao {

    @Autowired
    private MallTypeBrandMapper mallTypeBrandMapper;

    public MallTypeBrand findByTypeIdAndBrandId(Long typeId, Long brandId) {
        MallTypeBrand mallTypeBrand = new MallTypeBrand();
        mallTypeBrand.setTypeId(typeId);
        mallTypeBrand = mallTypeBrandMapper.selectOne(mallTypeBrand);
        return mallTypeBrand;
    }

    public int insert(MallTypeBrand mallTypeBrand) {
        int count = mallTypeBrandMapper.insert(mallTypeBrand);
        return count;
    }

    public int unboundBrand(Long id) {
        Example example = new Example(MallTypeBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = mallTypeBrandMapper.deleteByExample(example);
        return count;
    }


    public Page pageBrand(Page page) {
        String[] legalKeyWords = { "id", "typeId", "brandId"};
        PageHandler<MallTypeBrand> pageHandler = new PageHandler<MallTypeBrand>(MallTypeBrand.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        page = pageHandler.listPage(page, mallTypeBrandMapper);
        return page;
    }

}
