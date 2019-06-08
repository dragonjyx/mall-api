package com.mall.dao;

import com.mall.mapper.UserMapper;
import com.mall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public User findByUserInfoId(Long userInfoId){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userInfoId",userInfoId);
        List<User> userList = userMapper.selectByExample(example);
        if(userList.isEmpty()){
            return null;
        }
        return userList.get(0);
    }

    public User findByUserId(String userId){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("uid",userId);
        List<User> userList = userMapper.selectByExample(example);
        if(userList.isEmpty()){
            return null;
        }
        return userList.get(0);
    }



    public int updateUser(User user){
        if(user.getId() == null){
            return 0;
        }
        int result = userMapper.updateByPrimaryKey(user);
        return result;
    }


    public int addNewUser(User user){
        int result = userMapper.insertSelective(user);
        return result;
    }


    public User findByUnionId(String openId){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("unionId",openId);
        List<User> userList = userMapper.selectByExample(example);
        if(userList.isEmpty()){
            return null;
        }
        return userList.get(0);
    }




}
