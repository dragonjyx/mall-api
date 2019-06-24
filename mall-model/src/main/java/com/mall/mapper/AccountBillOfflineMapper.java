package com.mall.mapper;

import com.mall.model.AccountBillOffline;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountBillOfflineMapper extends Mapper<AccountBillOffline> {

    BigDecimal sumAmount(@Param("accountId") String accountId, @Param("type")Integer type, @Param("status") Integer status, @Param("billStatus") Integer billStatus,@Param("isSettle")Integer isSettle);

    List<AccountBillOffline> findBillList(@Param("accountId") String accountId, @Param("type")Integer type, @Param("stateDate")  Date stateDate, @Param("endDate")  Date endDate);
}
