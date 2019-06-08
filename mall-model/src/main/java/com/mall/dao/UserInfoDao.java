package com.mall.dao;

import com.mall.mapper.UserInfoMapper;
import com.mall.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserInfoDao {

    @Autowired
    private UserInfoMapper userInfoMapper;


    public UserInfo findByUserId(String userId){
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("uid",userId);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if(userInfoList.isEmpty()){
            return null;
        }
        return userInfoList.get(0);
    }

    public UserInfo findByEmail(String email){
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("email",email);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if(userInfoList.isEmpty()){
            return null;
        }
        return userInfoList.get(0);
    }

    public UserInfo findByPhone(String phone){
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("phone",phone);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if(userInfoList.isEmpty()){
            return null;
        }
        return userInfoList.get(0);
    }

    public UserInfo findById(long id){
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("id",id);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if(userInfoList.isEmpty()){
            return null;
        }
        return userInfoList.get(0);
    }


    public int addNewUserInfo(UserInfo userInfo){
        int result = userInfoMapper.insertSelective(userInfo);
        return result;
    }

    public int updateUserInfo(UserInfo userInfo){
        int result = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        return result;
    }



    public List<UserInfo> findMyUserlist(String userId, String name, String phone, String email, Integer status,Integer permissionCode){
        List<UserInfo> userInfoList = userInfoMapper.findMyUserlist(userId,name,phone,email,status,permissionCode);
        return userInfoList;
    }



}
