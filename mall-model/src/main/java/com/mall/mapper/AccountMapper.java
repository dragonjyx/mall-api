package com.mall.mapper;

import com.mall.model.Account;
import com.mall.params.resp.UserAccountInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AccountMapper extends Mapper<Account> {


    List<UserAccountInfo> findByNameOrPhoneOrEmail(@Param("name") String name, @Param("phone") String phone, @Param("email") String email, @Param("userId") String userId, @Param("status") Integer status);




}