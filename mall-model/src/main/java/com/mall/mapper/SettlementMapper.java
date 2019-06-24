package com.mall.mapper;

import com.mall.model.Settlement;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

public interface SettlementMapper extends Mapper<Settlement> {


    BigDecimal sumSettlementByUserIdAndStatus(@Param("userId") String userId,@Param("status") Integer status);

}
