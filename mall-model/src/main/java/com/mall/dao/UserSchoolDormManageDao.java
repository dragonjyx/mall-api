package com.mall.dao;

import com.mall.mapper.UserSchoolDormManageMapper;
import com.mall.model.UserSchoolDorm;
import com.mall.model.UserSchoolDormManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserSchoolDormManageDao {

    @Autowired
    private UserSchoolDormManageMapper userSchoolDormManageMapper;


    public UserSchoolDormManage findByDormId(Long dormId) {
        Example example = new Example(UserSchoolDormManage.class);
        example.createCriteria().andEqualTo("dormId",dormId);
        List<UserSchoolDormManage> userSchoolDormList = userSchoolDormManageMapper.selectByExample(example);
        if(userSchoolDormList.isEmpty()){
            return null;
        }
        return userSchoolDormList.get(0);
    }


    public List<UserSchoolDormManage> findByUserId(String userId) {
        Example example = new Example(UserSchoolDormManage.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<UserSchoolDormManage> userSchoolDormList = userSchoolDormManageMapper.selectByExample(example);
        return userSchoolDormList;
    }
}
