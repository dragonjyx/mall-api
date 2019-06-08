package com.mall.dao;

import com.mall.mapper.PermissionMapper;
import com.mall.mapper.RoleMapper;
import com.mall.model.Permission;
import com.mall.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class RoleDao {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> findAllPermission(Integer type) {
        Example example = new Example(Permission.class);
        if (type != null) {
            example.createCriteria().andEqualTo("type", type);
        }
        List<Permission> permissionList = permissionMapper.selectByExample(example);
        return permissionList;
    }

    public Role findByIdAndStatus(long id, Integer status){
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("status",status);
        List<Role> roleList = roleMapper.selectByExample(example);
        if(roleList.isEmpty()){
            return null;
        }
        return roleList.get(0);
    }


    public List<Role> findByUserIdAndStatusAndPlatformCode(String userId,Integer status,String code){
        List<Role> roleList = roleMapper.findByUserIdAndStatusAndPlatformCode(userId,status,code);
        return roleList;
    }


    public int addRole(Role role) {
        int result = roleMapper.insertSelective(role);
        return result;
    }



    public int updateRoleById(Role role) {
        Long id = role.getId();
        if (id == null) {
            log.error("id is null");
            return 0;
        }
        int result = roleMapper.updateByPrimaryKeySelective(role);
        return result;
    }


    public List<Role> findByPlatformCode(String platformCode) {
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("platformCode", platformCode);
        List<Role> roleList = roleMapper.selectByExample(example);
        return roleList;
    }

    public Role findByPlatformCodeAndIsSuper(String platformCode,boolean isSuper) {
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("platformCode", platformCode).andEqualTo("isSuper",isSuper);
        List<Role> roleList = roleMapper.selectByExample(example);
        if(roleList.isEmpty()){
            return null;
        }
        return roleList.get(0);
    }

    public Role findByRoleNo(String roleNo) {
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("roleNo", roleNo);
        List<Role> roleList = roleMapper.selectByExample(example);
        if(roleList.isEmpty()){
            return null;
        }
        return roleList.get(0);
    }

    public List<Role> findByNameOrCodeOrStatus(String name,Integer status) {
        return roleMapper.findByNameOrCodeOrStatus(name, status);
    }

    public List<Role> findByNameOrCodeOrStatusAndUserId(String name,Integer status,String userId) {
        return roleMapper.findByNameOrCodeOrStatusAndUserId(name, status , userId);
    }

    public int updateStatus(Long id, Integer status) {
        return roleMapper.updateStatus(id, status);
    }

    public int deleteById(long id){
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("id", id);
        int result = roleMapper.deleteByExample(example);
        return result;
    }


}
