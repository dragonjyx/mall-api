package com.mall.mapper;

import com.mall.model.MallBrand;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MallBrandMapper extends Mapper<MallBrand> {

    int updateMallBrandByCategoryId(@Param("categoryId") Long categoryId);
}