package com.mall.dao;

import com.mall.mapper.AccountRankMapper;
import com.mall.model.AccountRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AccountRankDao {

    @Autowired
    private AccountRankMapper accountRankMapper;

    public List<AccountRank> allRank(){
        Example example = new Example(AccountRank.class);
        List<AccountRank> accountRankList = accountRankMapper.selectByExample(example);
        return accountRankList;
    }

}
