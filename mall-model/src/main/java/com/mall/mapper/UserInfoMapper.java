package com.mall.mapper;

import com.mall.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {

    List<UserInfo> findMyUserlist(@Param("userId") String userId, @Param("name") String name, @Param("phone") String phone, @Param("email") String email, @Param("status") Integer status, @Param("permissionCode") Integer permissionCode);

}