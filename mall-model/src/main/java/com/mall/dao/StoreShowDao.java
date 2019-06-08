package com.mall.dao;

import com.mall.mapper.StoreShowMapper;
import com.mall.model.StoreShow;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class StoreShowDao {

    @Autowired
    private StoreShowMapper storeShowMapper;

    public StoreShow findByDeviceSn(String deviceSn) {

        StoreShow storeShow = new StoreShow();
        storeShow.setDeviceSn(deviceSn);
        return storeShowMapper.selectOne(storeShow);
    }

    public int insert(StoreShow storeShow) {
        int count = storeShowMapper.insert(storeShow);
        return count;
    }

    public StoreShow findById(Long id) {
        StoreShow storeShow = new StoreShow();
        storeShow.setId(id);
        return storeShowMapper.selectOne(storeShow);
    }

    public int updateById(StoreShow storeShow) {
        Example example = new Example(StoreShow.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", storeShow.getId());
        int count = storeShowMapper.updateByExample(storeShow, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(StoreShow.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        return storeShowMapper.deleteByExample(example);
    }

    public Page page(Page page) {

        String[] legalKeyWords = { "id", "storeId", "deviceTypeId", "goodsSn", "goodsCode", "deviceSn"};
        String[] legalLikeWords = { "deviceTypeName"};
        String[] legalOrderWords = { "showStart", "showEnd", "id"};
        PageHandler<StoreShow> pageHandler = new PageHandler<StoreShow>(StoreShow.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, storeShowMapper);
        return page;
    }
}
