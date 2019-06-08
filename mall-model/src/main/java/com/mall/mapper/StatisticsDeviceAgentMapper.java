package com.mall.mapper;

import com.mall.model.StatisticsDeviceAgent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatisticsDeviceAgentMapper extends Mapper<StatisticsDeviceAgent> {


    int insertBatch(@Param("agents") List<StatisticsDeviceAgent> agents);

    int updateBatch(@Param("agents") List<StatisticsDeviceAgent> agents);
}