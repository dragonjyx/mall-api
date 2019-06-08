package com.mall.mapper;

import com.mall.model.Region;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface RegionMapper extends Mapper<Region> {

    List<Region> findByParentId(@Param("parentId") String parentId);

}