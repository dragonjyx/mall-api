package com.mall.dao;

import com.mall.mapper.UserSchoolDormSupplierMapper;
import com.mall.model.UserSchoolDormSupplier;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserSchoolDormSupplierDao {

    @Autowired
    private UserSchoolDormSupplierMapper userSchoolDormSupplierMapper;


    public UserSchoolDormSupplier findBySchoolId(Long schoolId) {
        Example example = new Example(UserSchoolDormSupplier.class);
        example.createCriteria().andEqualTo("schoolId",schoolId);
        List<UserSchoolDormSupplier> userSchoolDormSupplierList = userSchoolDormSupplierMapper.selectByExample(example);
        if(userSchoolDormSupplierList.isEmpty()){
            return null;
        }
        return userSchoolDormSupplierList.get(0);
    }

    public List<UserSchoolDormSupplier> findByUserId(String userId) {
        Example example = new Example(UserSchoolDormSupplier.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<UserSchoolDormSupplier> userSchoolDormSupplierList = userSchoolDormSupplierMapper.selectByExample(example);
        return userSchoolDormSupplierList;
    }
}
