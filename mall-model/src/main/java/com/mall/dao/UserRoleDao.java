package com.mall.dao;

import com.mall.mapper.UserRoleMapper;
import com.mall.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserRoleDao {

    @Autowired
    private UserRoleMapper userRoleMapper;



    public List<UserRole> findByUserId(String userId){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("userId",userId);

        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
        return userRoleList;
    }


    public List<UserRole> findByRoleId(Long roleId){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("roleId",roleId);

        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
        return userRoleList;
    }


    public int deleteByRoleId(Long roleId){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("roleId",roleId);
        int result = userRoleMapper.deleteByExample(example);
        return result;
    }


    public UserRole findByRoleIdAndUserId(Long roleId,String userId){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("roleId",roleId).andEqualTo("userId",userId);
        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
        if(userRoleList.isEmpty()){
            return null;
        }
        return userRoleList.get(0);
    }

    public UserRole findByRoleIdAndParentUserId(Long roleId,String parentUserId){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("roleId",roleId).andEqualTo("parentUid",parentUserId);
        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
        if(userRoleList.isEmpty()){
            return null;
        }
        return userRoleList.get(0);
    }


    public int delUserRoleByUserId(String userId){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("userId",userId);
        return userRoleMapper.deleteByExample(example);
    }

    public int addUserRole(String parentUserId,String userId,Long roleId){
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setParentUid(parentUserId);
        return userRoleMapper.insertSelective(userRole);
    }




}
