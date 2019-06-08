package com.mall.dao;

import com.mall.mapper.RoleResourceMapper;
import com.mall.model.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RoleResourceDao {

    @Autowired
    private RoleResourceMapper roleResourceMapper;


    public int addRoleResource(RoleResource roleResource){
        if(roleResource.getRoleId() == null || roleResource.getResourceId() == null){
            return 0;
        }
        roleResource.setId(null);
        int result = roleResourceMapper.insertSelective(roleResource);
        return result;
    }

    public int delById(long id){
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("id",id);
        int result = roleResourceMapper.deleteByExample(example);
        return result;
    }

    public int delByRoleId(long roleId){
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("roleId",roleId);
        int result = roleResourceMapper.deleteByExample(example);
        return result;
    }

    public int delByResourceId(long resourceId){
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("resourceId",resourceId);
        int result = roleResourceMapper.deleteByExample(example);
        return result;
    }

    public RoleResource findById(long id){
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("id",id);
        List<RoleResource> roleResourceList= roleResourceMapper.selectByExample(example);
        if(roleResourceList.isEmpty()){
            return null;
        }
        return roleResourceList.get(0);
    }

    public List<RoleResource>  findByResourceId(long resourceId){
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("resourceId",resourceId);
        List<RoleResource> roleResourceList= roleResourceMapper.selectByExample(example);
        return roleResourceList;
    }

    public List<RoleResource> findByRoleId(long roleId){
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("roleId",roleId);
        List<RoleResource> roleResourceList= roleResourceMapper.selectByExample(example);
        return roleResourceList;
    }

}
