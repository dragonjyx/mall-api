package com.mall.mapper;

import com.mall.model.MallAttributeValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MallAttributeValueMapper extends Mapper<MallAttributeValue> {

    int updateMallAttributeValueByAttrId(@Param("attrId") Long attrId);

    int updateMallAttributeValueByTypeId(@Param("typeId") Long typeId);
}