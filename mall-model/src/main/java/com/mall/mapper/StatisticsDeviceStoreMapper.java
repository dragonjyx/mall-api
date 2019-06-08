package com.mall.mapper;

import com.mall.model.StatisticsDeviceStore;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatisticsDeviceStoreMapper extends Mapper<StatisticsDeviceStore> {

    int insertBatch(@Param("stores") List<StatisticsDeviceStore> stores);

    int updateBatch(@Param("stores") List<StatisticsDeviceStore> stores);
}