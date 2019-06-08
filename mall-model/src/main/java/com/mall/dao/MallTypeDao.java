package com.mall.dao;

import com.mall.mapper.MallTypeMapper;
import com.mall.model.MallType;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallTypeDao {

    @Autowired
    private MallTypeMapper mallTypeMapper;

    public MallType findByTypeName(String typeName) {
        MallType mallType = new MallType();
        mallType.setTypeName(typeName);
        return mallTypeMapper.selectOne(mallType);
    }

    public int insert(MallType mallType) {

        return mallTypeMapper.insertSelective(mallType);
    }

    public int updateById(MallType mallType) {

        Example example = new Example(MallType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", mallType.getId());
        return mallTypeMapper.updateByExampleSelective(mallType, example);
    }

    public int deleteById(Long id) {
        Example example = new Example(MallType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        return mallTypeMapper.deleteByExample(example);
    }

    public MallType findById(Long id) {

        MallType mallType = new MallType();
        mallType.setId(id);
        return mallTypeMapper.selectOne(mallType);
    }

    public Page pageMallType(Page page) {
        String[] legalKeyWords = { "id", "status"};
        String[] legalLikeWords = { "typeName"};
        String[] legalOrderWords = { "sort", "id"};
        PageHandler<MallType> pageHandler = new PageHandler<MallType>(MallType.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallTypeMapper);
        return page;
    }


    public List<MallType> findByIds(List<Object> typeIds) {
        Example example = new Example(MallType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", typeIds);
        List<MallType> mallTypes = mallTypeMapper.selectByExample(example);
        return  mallTypes;
    }


    public List<MallType> findAllType(){
        Example example = new Example(MallType.class);
        List<MallType> mallTypes = mallTypeMapper.selectByExample(example);
        return  mallTypes;
    }



}
