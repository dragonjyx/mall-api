package com.mall.mapper;

import com.mall.model.OrderCommon;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderCommonMapper extends Mapper<OrderCommon> {

    List<OrderCommon> findMerchantOrderList(@Param("userId") String userId,@Param("status") Integer status,@Param("isDelete") Integer isDelete);

    int setExpireByOrderSn(@Param("orderSn") String orderSn,@Param("status") Integer status);

}