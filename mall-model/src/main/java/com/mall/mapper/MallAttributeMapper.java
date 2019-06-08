package com.mall.mapper;

import com.mall.model.MallAttribute;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MallAttributeMapper extends Mapper<MallAttribute> {

    int updateMallAttributeByTypeId(@Param("typeId") Long typeId);
}