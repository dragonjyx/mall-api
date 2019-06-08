package com.mall.mapper;

import com.mall.model.StatisticsOrderAgent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatisticsOrderAgentMapper extends Mapper<StatisticsOrderAgent> {


    int insertBatch(@Param("agents") List<StatisticsOrderAgent> agents);

    int updateBatch(@Param("agents") List<StatisticsOrderAgent> agents);
}