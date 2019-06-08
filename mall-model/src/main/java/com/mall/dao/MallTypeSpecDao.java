package com.mall.dao;

import com.mall.mapper.MallTypeSpecMapper;
import com.mall.model.MallTypeSpec;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class MallTypeSpecDao {

    @Autowired
    private MallTypeSpecMapper mallTypeSpecMapper;

    public MallTypeSpec findByTypeIdAndSpecId(Long typeId, Long specId) {
        MallTypeSpec mallTypeSpec = new MallTypeSpec();
        mallTypeSpec.setSpecId(specId);
        mallTypeSpec.setTypeId(typeId);
        mallTypeSpec = mallTypeSpecMapper.selectOne(mallTypeSpec);
        return mallTypeSpec;
    }

    public int insert(MallTypeSpec mallTypeSpec) {
        int count = mallTypeSpecMapper.insert(mallTypeSpec);
        return count;
    }

    public int unboundSpec(Long id) {
        Example example = new Example(MallTypeSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = mallTypeSpecMapper.deleteByExample(example);
        return count;
    }

    public Page pageSpec(Page page) {
        String[] legalKeyWords = { "id", "typeId", "specId"};
        PageHandler<MallTypeSpec> pageHandler = new PageHandler<MallTypeSpec>(MallTypeSpec.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        page = pageHandler.listPage(page, mallTypeSpecMapper);
        return page;
    }
}
