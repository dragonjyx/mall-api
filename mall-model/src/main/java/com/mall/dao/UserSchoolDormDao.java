package com.mall.dao;

import com.mall.mapper.UserSchoolDormMapper;
import com.mall.model.UserSchoolDorm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserSchoolDormDao {

    @Autowired
    private UserSchoolDormMapper userSchoolDormMapper;


    public List<UserSchoolDorm> findByUserId(String userId){
        Example example = new Example(UserSchoolDorm.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<UserSchoolDorm> userSchoolDormList = userSchoolDormMapper.selectByExample(example);
        return userSchoolDormList;
    }


    public UserSchoolDorm findByDormId(Long dormId) {
        Example example = new Example(UserSchoolDorm.class);
        example.createCriteria().andEqualTo("dormId",dormId);
        List<UserSchoolDorm> userSchoolDormList = userSchoolDormMapper.selectByExample(example);
        if(userSchoolDormList.isEmpty()){
            return null;
        }
        return userSchoolDormList.get(0);
    }

    public UserSchoolDorm findByUserIdAndDromId(String userId, Long dormId) {
        Example example = new Example(UserSchoolDorm.class);
        example.createCriteria().andEqualTo("dormId",dormId).andEqualTo("userId",userId);
        List<UserSchoolDorm> userSchoolDormList = userSchoolDormMapper.selectByExample(example);
        if(userSchoolDormList.isEmpty()){
            return null;
        }
        return userSchoolDormList.get(0);
    }
}
