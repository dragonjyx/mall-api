package com.mall.mapper;

import com.mall.model.MallSpec;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MallSpecMapper extends Mapper<MallSpec> {

    int updateMallSpecByCategoryId(@Param("categoryId") Long categoryId);
}