package com.mall.mapper;

import com.mall.model.StatisticsDeviceType;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatisticsDeviceTypeMapper extends Mapper<StatisticsDeviceType> {


    int insertBatch(@Param("deviceTypes") List<StatisticsDeviceType> deviceTypes);

    int updateBatch(@Param("deviceTypes") List<StatisticsDeviceType> deviceTypes);
}