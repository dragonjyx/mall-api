package com.mall.dao;

import com.mall.mapper.MallGoodsGalleryMapper;
import com.mall.model.MallGoodsGallery;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallGoodsGalleryDao {

    @Autowired
    private MallGoodsGalleryMapper mallGoodsGalleryMapper;

    public int insert(MallGoodsGallery mallGoodsGallery) {

        int count = mallGoodsGalleryMapper.insert(mallGoodsGallery);
        return count;
    }

    public MallGoodsGallery findById(Long id) {
        MallGoodsGallery mallGoodsGallery = new MallGoodsGallery();
        mallGoodsGallery.setId(id);
        mallGoodsGallery = mallGoodsGalleryMapper.selectOne(mallGoodsGallery);
        return mallGoodsGallery;
    }

    public int updateById(MallGoodsGallery mallGoodsGallery) {
        Example example = new Example(MallGoodsGallery.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", mallGoodsGallery.getId());
        int count = mallGoodsGalleryMapper.updateByExample(mallGoodsGallery, example);
        return count;
    }

    public int deleteMallGoodsGallery(Long id) {
        Example example = new Example(MallGoodsGallery.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = mallGoodsGalleryMapper.deleteByExample(example);
        return count;
    }

    public int deleteMallGoodsGalleryByGoodsSn(String goodsSn) {
        Example example = new Example(MallGoodsGallery.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        int count = mallGoodsGalleryMapper.deleteByExample(example);
        return count;
    }

    public List<MallGoodsGallery> findByGoodsSn(String goodsSn) {
        Example example = new Example(MallGoodsGallery.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        List<MallGoodsGallery> mallGoodsGalleries = mallGoodsGalleryMapper.selectByExample(example);
        return mallGoodsGalleries;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "goodsSn"};
        String[] legalOrderWords = { "id"};
        PageHandler<MallGoodsGallery> pageHandler = new PageHandler<MallGoodsGallery>(MallGoodsGallery.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallGoodsGalleryMapper);
        return page;
    }

    public int deleteByGoodsSnAndNotIds(String goodsSn, List<Object> ids) {
        Example example = new Example(MallGoodsGallery.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsSn", goodsSn);
        if (ids != null && ids.size() != 0) {
            criteria.andNotIn("id", ids);
        }
        int count = mallGoodsGalleryMapper.deleteByExample(example);
        return count;
    }
}
