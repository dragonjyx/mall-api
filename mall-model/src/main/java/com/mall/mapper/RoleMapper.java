package com.mall.mapper;

import com.mall.model.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    List<Role> findByNameOrCodeOrStatus(@Param("name") String name, @Param("status") Integer status);

    List<Role> findByNameOrCodeOrStatusAndUserId(@Param("name") String name, @Param("status") Integer status, @Param("userId") String userId);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    List<Role> findByUserIdAndStatusAndPlatformCode(@Param("userId") String userId, @Param("status") Integer status, @Param("platformCode") String platformCode);
}