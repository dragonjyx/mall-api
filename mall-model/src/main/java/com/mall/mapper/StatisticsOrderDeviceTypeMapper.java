package com.mall.mapper;

import com.mall.model.StatisticsOrderDeviceType;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatisticsOrderDeviceTypeMapper extends Mapper<StatisticsOrderDeviceType> {


    int insertBatch(@Param("deviceTypes") List<StatisticsOrderDeviceType> deviceTypes);

    int updateBatch(@Param("deviceTypes") List<StatisticsOrderDeviceType> deviceTypes);
}