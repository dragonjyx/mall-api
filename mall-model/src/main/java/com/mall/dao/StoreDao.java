package com.mall.dao;

import com.mall.mapper.StoreMapper;
import com.mall.model.Store;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class StoreDao {

    @Autowired
    private StoreMapper storeMapper;

    public int insert(Store store) {

        int count = storeMapper.insert(store);
        return count;
    }

    public Store findById(Long id) {
        Store store = new Store();
        store.setId(id);
        return storeMapper.selectOne(store);
    }

    public int updateById(Store store) {
        Example example = new Example(Store.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", store.getId());
        int count = storeMapper.updateByExample(store, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(Store.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = storeMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "uid", "score", "status"};
        String[] legalLikeWords = { "name", "address", "storeAbstract", "staff", "province", "city", "district"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<Store> pageHandler = new PageHandler<Store>(Store.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, storeMapper);
        return page;
    }

    public List<Store> findByStatus(Integer status) {
        Example example = new Example(Store.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", status);
        List<Store> stores = storeMapper.selectByExample(example);
        return stores;
    }
}
