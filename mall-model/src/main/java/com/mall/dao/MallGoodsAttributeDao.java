package com.mall.dao;

import com.mall.mapper.MallGoodsAttributeMapper;
import com.mall.model.MallGoodsAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallGoodsAttributeDao {

    @Autowired
    private MallGoodsAttributeMapper mallGoodsAttributeMapper;

    public int insert(MallGoodsAttribute mallGoodsAttribute) {
        int count = mallGoodsAttributeMapper.insert(mallGoodsAttribute);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(MallGoodsAttribute.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = mallGoodsAttributeMapper.deleteByExample(example);
        return count;
    }

    public List<MallGoodsAttribute> findByGoodsSn(String goodsSn) {
        Example example = new Example(MallGoodsAttribute.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        List<MallGoodsAttribute> mallGoodsAttributes = mallGoodsAttributeMapper.selectByExample(example);
        return mallGoodsAttributes;
    }

    public int deleteByGoodsSn(String goodsSn) {
        Example example = new Example(MallGoodsAttribute.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        int count = mallGoodsAttributeMapper.deleteByExample(example);
        return count;
    }
}
