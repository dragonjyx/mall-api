package com.mall.mapper;

import com.mall.model.AccountBill;
import com.mall.params.resp.AccountBillResp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountBillMapper extends Mapper<AccountBill> {


    List<AccountBillResp> findByNameOrPhoneOrEmail(@Param("name") String name, @Param("phone") String phone,
                                                   @Param("email") String email, @Param("userId") String userId,
                                                   @Param("status") Integer status, @Param("type") Integer type,
                                                   @Param("stateDate") Date stateDate, @Param("endDate") Date endDate);

    List<AccountBillResp> findByType(@Param("userId") String userId, @Param("status") Integer status,
                                     @Param("type") Integer type, @Param("stateDate") Date stateDate, @Param("endDate") Date endDate);


    BigDecimal sumAmount(@Param("accountId") String accountId,@Param("type")Integer type, @Param("status") Integer status, @Param("billStatus") Integer billStatus);

    List<AccountBill> findBillList(@Param("accountId") String accountId,@Param("type")Integer type,@Param("stateDate")  Date stateDate,@Param("endDate")  Date endDate);
}