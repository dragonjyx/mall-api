package com.mall.dao;

import com.mall.mapper.AccountMapper;
import com.mall.model.Account;
import com.mall.params.resp.UserAccountInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AccountDao {

    @Autowired
    private AccountMapper accountMapper;


    public Account findById(long id){
        Example example = new Example(Account.class);
        example.createCriteria().andEqualTo("id",id);
        List<Account> accountList = accountMapper.selectByExample(example);
        if(accountList.isEmpty()){
            return null;
        }
        return accountList.get(0);
    }

    public Account findByAccountId(String aid){
        Example example = new Example(Account.class);
        example.createCriteria().andEqualTo("aid",aid);
        List<Account> accountList = accountMapper.selectByExample(example);
        if(accountList.isEmpty()){
            return null;
        }
        return accountList.get(0);
    }

    public Account findByUserId(String userId){
        Example example = new Example(Account.class);
        example.createCriteria().andEqualTo("uid",userId);
        List<Account> accountList = accountMapper.selectByExample(example);
        if(accountList.isEmpty()){
            return null;
        }
        return accountList.get(0);
    }


    public int updateAccount(Account account){
        if(account.getId() == null){
            return 0;
        }
        Integer oldVersion = account.getVersion();
        Integer version = account.getVersion();
        version++;
        account.setVersion(version);

        Example example = new Example(Account.class);
        example.createCriteria().andEqualTo("id",account.getId()).andEqualTo("version",oldVersion);
        int result = accountMapper.updateByExample(account,example);
        return result;
    }

    public int addAccount(Account account){
        if(StringUtils.isEmpty(account.getAid())){
            return 0;
        }
        int result = accountMapper.insertSelective(account);
        return result;
    }


    public List<UserAccountInfo> findByNameOrPhoneOrEmailAndParentUserid(String name, String phone, String email, String userId, Integer status){
        return accountMapper.findByNameOrPhoneOrEmail(name,phone,email,userId,status);
    }




}
