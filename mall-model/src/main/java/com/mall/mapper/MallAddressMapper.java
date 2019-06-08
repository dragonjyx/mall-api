package com.mall.mapper;

import com.mall.model.MallAddress;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MallAddressMapper extends Mapper<MallAddress> {

    int setAddressDetault(@Param("memberId") String memberId,@Param("addressSn") String addressSn,@Param("isDefault") Integer isDefault);

}