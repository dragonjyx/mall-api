package com.mall.dao;

import com.mall.mapper.UserMemberMapper;
import com.mall.model.UserMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserMemberDao {

    @Autowired
    private UserMemberMapper userMemberMapper;


    public int addNewUserMember(UserMember userMember){
        int result = userMemberMapper.insertSelective(userMember);
        return result;
    }

    public int deleteUserMember(long id){
        Example example = new Example(UserMember.class);
        example.createCriteria().andEqualTo("id",id);
        int result = userMemberMapper.deleteByExample(example);
        return result;
    }



}

