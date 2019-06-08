package com.mall.mapper;

import com.mall.model.OrderGoods;
import com.mall.params.req.UserOrderGoodsDevice;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OrderGoodsMapper extends Mapper<OrderGoods> {

    /**
     * 查询已发货设备订单
     * @param deviceSn
     * @param goodsSn
     * @param goodsCode
     * @return
     */
    UserOrderGoodsDevice findByDeviceSnAndGoods(@Param("deviceSn") String deviceSn, @Param("goodsSn") String goodsSn, @Param("goodsCode") String goodsCode);



}