package com.mall.dao;

import com.mall.mapper.StoreUserMapper;
import com.mall.model.StoreUser;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class StoreUserDao {

    @Autowired
    private StoreUserMapper storeUserMapper;

    public int insert(StoreUser storeUser) {
        int count = storeUserMapper.insert(storeUser);
        return count;
    }

    public StoreUser findById(Long id) {
        StoreUser storeUser = new StoreUser();
        storeUser.setId(id);
        storeUser = storeUserMapper.selectOne(storeUser);
        return storeUser;
    }

    public int updateById(StoreUser storeUser) {
        Example example = new Example(StoreUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", storeUser.getId());
        int count = storeUserMapper.updateByExample(storeUser, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(StoreUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = storeUserMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "storeId", "uid"};
        String[] legalLikeWords = { "userName", "job"};
        String[] legalOrderWords = { "id", "createTime"};
        PageHandler<StoreUser> pageHandler = new PageHandler<StoreUser>(StoreUser.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, storeUserMapper);
        return page;
    }
}
