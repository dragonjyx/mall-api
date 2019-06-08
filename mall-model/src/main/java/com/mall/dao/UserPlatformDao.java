package com.mall.dao;

import com.mall.mapper.UserPlatformMapper;
import com.mall.model.UserPlatform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class UserPlatformDao {

    @Autowired
    private UserPlatformMapper userPlatformMapper;


    public int addRelation(String userId,String platformCode,String platformName){
        UserPlatform userPlatform = new UserPlatform();
        userPlatform.setUid(userId);
        userPlatform.setPlatformCode(platformCode);
        userPlatform.setName(platformName);
        int result = userPlatformMapper.insertSelective(userPlatform);
        return result;
    }

    public List<UserPlatform> findByPlatformCode(String platformCode){
        Example example = new Example(UserPlatform.class);
        example.createCriteria().andEqualTo("platformCode",platformCode);
        List<UserPlatform> userPlatformList = userPlatformMapper.selectByExample(example);
        return userPlatformList;
    }

    public List<UserPlatform> findByUserId(String userId){
        Example example = new Example(UserPlatform.class);
        example.createCriteria().andEqualTo("uid",userId);
        List<UserPlatform> userPlatformList = userPlatformMapper.selectByExample(example);
        return userPlatformList;
    }

    public UserPlatform findByUserIdAndCode(String userId,String platformCode){
        Example example = new Example(UserPlatform.class);
        example.createCriteria().andEqualTo("uid",userId).andEqualTo("platformCode",platformCode);
        List<UserPlatform> userPlatformList = userPlatformMapper.selectByExample(example);
        if(userPlatformList.isEmpty()){
            return null;
        }
        return userPlatformList.get(0);
    }

    public int delRelation(String userId,String platformCode){
        Example example = new Example(UserPlatform.class);
        example.createCriteria().andEqualTo("uid",userId).andEqualTo("platformCode",platformCode);
        int result = userPlatformMapper.deleteByExample(example);
        return result;
    }

    public int delRelation(String userId){
        Example example = new Example(UserPlatform.class);
        example.createCriteria().andEqualTo("uid",userId);
        int result = userPlatformMapper.deleteByExample(example);
        return result;
    }


    public int delRelationById(long id){
        Example example = new Example(UserPlatform.class);
        example.createCriteria().andEqualTo("id",id);
        int result = userPlatformMapper.deleteByExample(example);
        return result;
    }



}
