package com.mall.mapper;

import com.mall.model.MallCategory;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MallCategoryMapper extends Mapper<MallCategory> {

    int updateMallCategoryByTypeId(@Param("typeId") Long typeId);
}