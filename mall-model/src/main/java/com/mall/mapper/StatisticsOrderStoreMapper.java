package com.mall.mapper;

import com.mall.model.StatisticsOrderStore;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatisticsOrderStoreMapper extends Mapper<StatisticsOrderStore> {

    int insertBatch(@Param("stores") List<StatisticsOrderStore> stores);

    int updateBatch(@Param("stores") List<StatisticsOrderStore> stores);
}