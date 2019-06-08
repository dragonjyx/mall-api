package com.mall.mapper;

import com.mall.model.Platform;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PlatformMapper extends Mapper<Platform> {


    List<Platform> findByNameOrCode(@Param("name") String name, @Param("code") String code, @Param("status") Integer status);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);


}