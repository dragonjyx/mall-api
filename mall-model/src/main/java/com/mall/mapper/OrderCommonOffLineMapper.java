package com.mall.mapper;

import com.mall.model.OrderCommon;
import com.mall.model.OrderCommonOffLine;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderCommonOffLineMapper extends Mapper<OrderCommonOffLine> {
    List<OrderCommonOffLine> findMerchantOrderList(@Param("userId") String userId, @Param("status") Integer status, @Param("isDelete") Integer isDelete);

}
