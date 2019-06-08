package com.mall.mapper;

import com.mall.model.MallSpecValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MallSpecValueMapper extends Mapper<MallSpecValue> {

    int updateMallSpecValueBySpecId(@Param("specId") Long specId);

    int updateMallSpecValueByCategoryId(@Param("categoryId") Long categoryId);
}