package com.mall.mapper;

import com.mall.model.AfterSaleMemberDevice;
import com.mall.params.resp.MemberAddEquipment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AfterSaleMemberDeviceMapper extends Mapper<AfterSaleMemberDevice> {

    List<MemberAddEquipment> findByCategoryIdAndMemberId(@Param("memberId") String memberId, @Param("categoryId") Long categoryId);


}
