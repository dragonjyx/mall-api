package com.mall.mapper;

import com.mall.model.MallGoodsCommon;
import com.mall.params.resp.MallGoodsCommonResp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface MallGoodsCommonMapper extends Mapper<MallGoodsCommon> {

    List<MallGoodsCommonResp> findPage(@Param("categoryId") Integer categoryId, @Param("name") String name, @Param("brandName") String brandName, @Param("isCommend") Integer isCommend,
                                       @Param("isHot") Integer isHot, @Param("regionId") Integer regionId, @Param("bottomPrice") String bottomPrice,
                                       @Param("topPrice") String topPrice, @Param("startLine") Integer startLine, @Param("pageSize") Integer pageSize,
                                       @Param("orderBy") String orderBy);

    int findPageCount(@Param("categoryId") Integer categoryId, @Param("name") String name, @Param("brandName") String brandName, @Param("isCommend") Integer isCommend,
                      @Param("isHot") Integer isHot, @Param("regionId") Integer regionId, @Param("bottomPrice") String bottomPrice,
                      @Param("topPrice") String topPrice);
}