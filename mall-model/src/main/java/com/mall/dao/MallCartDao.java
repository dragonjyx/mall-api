package com.mall.dao;

import com.mall.mapper.MallCartMapper;
import com.mall.model.MallCart;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallCartDao {

    @Autowired
    private MallCartMapper mallCartMapper;

    public MallCart findByUidAndGoodsCode(String uid, String goodsCode) {

        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        criteria.andEqualTo("goodsCode", goodsCode);
        List<MallCart> mallCarts = mallCartMapper.selectByExample(example);
        if (mallCarts == null || mallCarts.size() == 0) {
            return null;
        }
        return mallCarts.get(0);
    }

    public int insert(MallCart mallCart) {
        int count = mallCartMapper.insert(mallCart);
        return count;
    }

    public MallCart findById(Long id) {
        MallCart mallCart = new MallCart();
        mallCart.setId(id);
        mallCart = mallCartMapper.selectOne(mallCart);
        return mallCart;
    }

    public int updateById(MallCart mallCart) {
        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", mallCart.getId());
        int count = mallCartMapper.updateByExample(mallCart, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int result = mallCartMapper.deleteByExample(example);
        return result;
    }

    public List<MallCart> findMallCatByUid(String uid) {
        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        List<MallCart> mallCarts = mallCartMapper.selectByExample(example);
        return mallCarts;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "uid", "goodsCode", "goodsSn"};
        String[] legalLikeWords = { "goodsName", "specValue", "remark"};
        String[] legalOrderWords = { "marketPrice", "goodsPrice", "buyNum"};
        PageHandler<MallCart> pageHandler = new PageHandler<MallCart>(MallCart.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallCartMapper);
        return page;
    }

    public int deleteBatch(List<Object> ids, String uid) {
        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        criteria.andIn("id", ids);
        int count = mallCartMapper.deleteByExample(example);
        return count;
    }

    public List<MallCart> findBatch(List<Object> ids, String uid) {
        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        criteria.andIn("id", ids);
        List<MallCart> mallCarts = mallCartMapper.selectByExample(example);
        return mallCarts;
    }

    public int deleteAllCart(String memberId) {
        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",memberId);
        int count = mallCartMapper.deleteByExample(example);
        return count;
    }

    public MallCart findByGoodsSnAndGoodsCode(String goodsSn, String goodsCode) {
        Example example = new Example(MallCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn",goodsSn).andEqualTo("goodsCode",goodsCode);
        List<MallCart> mallCarts = mallCartMapper.selectByExample(example);
        if(mallCarts.isEmpty()){
            return null;
        }
        return mallCarts.get(0);
    }
}
