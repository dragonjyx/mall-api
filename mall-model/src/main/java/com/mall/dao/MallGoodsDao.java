package com.mall.dao;

import com.mall.mapper.MallGoodsMapper;
import com.mall.model.MallGoods;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallGoodsDao {

    @Autowired
    private MallGoodsMapper mallGoodsMapper;


    public MallGoods findByName(String name) {
        MallGoods mallGoods = new MallGoods();
        mallGoods.setName(name);
        mallGoods = mallGoodsMapper.selectOne(mallGoods);
        return mallGoods;
    }

    public int insert(MallGoods mallGoods) {

        int count = mallGoodsMapper.insert(mallGoods);
        return count;
    }


    public MallGoods findByCode(String code) {
        MallGoods mallGoods = new MallGoods();
        mallGoods.setCode(code);
        mallGoods = mallGoodsMapper.selectOne(mallGoods);
        return mallGoods;
    }

    public int updateByCode(MallGoods goods) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", goods.getCode());
        int count = mallGoodsMapper.updateByExample(goods, example);
        return count;
    }

    public int deleteMallGoods(String code) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        int count = mallGoodsMapper.deleteByExample(example);
        return count;
    }

    public int findCountByGoodsSn(String goodsSn) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        int count = mallGoodsMapper.selectCountByExample(example);
        return count;
    }

    public int deleteMallGoodsByGoodsSn(String goodsSn) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        int count = mallGoodsMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "goodsSn", "code", "barCode", "isPromotion", "permissionCode", "isDelete", "stockNum",
                "deviceTypeId", "factoryId"};
        String[] legalLikeWords = { "name", "specIds", "specCodes", "specValues", "promotionType", "remark", "deviceTypeName",
                "factoryName"};
        String[] legalOrderByWords = { "createTime", "marketPrice", "price", "promotionPrice", "promotionEnd", "limitNum",
                "updateTime", "stockNum"};
        PageHandler<MallGoods> pageHandler = new PageHandler<MallGoods>(MallGoods.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderByWords);
        page = pageHandler.listPage(page, mallGoodsMapper);
        return page;
    }

    public List<MallGoods> findByGoodsSn(String goodsSn) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        List<MallGoods> mallGoods = mallGoodsMapper.selectByExample(example);
        return mallGoods;
    }

    public int updateSelectiveByGoodsSn(MallGoods goods) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goods.getGoodsSn());
        int count = mallGoodsMapper.updateByExampleSelective(goods, example);
        return count;
    }

    public int deleteByGoodsSnAndNotCodes(String goodsSn, List<Object> codes) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        criteria.andNotIn("code", codes);
        int count =  mallGoodsMapper.deleteByExample(example);
        return count;
    }

    public MallGoods findByGoodsSnAndGoodsCode(String goodsSn, String goodsCode) {
        Example example = new Example(MallGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn).andEqualTo("code", goodsCode);
        List<MallGoods> mallGoods = mallGoodsMapper.selectByExample(example);
        if(mallGoods.isEmpty()){
            return null;
        }
        return mallGoods.get(0);
    }
}
