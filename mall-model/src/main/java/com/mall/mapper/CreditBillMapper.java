package com.mall.mapper;

import com.mall.model.CreditBill;
import com.mall.params.resp.CreditBillResp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface CreditBillMapper extends Mapper<CreditBill> {

    List<CreditBillResp> findByNameOrPhoneOrEmail(@Param("name") String name, @Param("phone") String phone,
                                                  @Param("email") String email, @Param("userId") String userId,
                                                  @Param("type") Integer type,
                                                  @Param("stateDate") Date stateDate, @Param("endDate") Date endDate);

    List<CreditBillResp> findByType(@Param("userId") String userId, @Param("type") Integer type,
                                    @Param("stateDate") Date stateDate, @Param("endDate") Date endDate);

}
