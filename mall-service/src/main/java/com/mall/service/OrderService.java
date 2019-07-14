package com.mall.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.mall.model.OrderCommon;
import com.mall.model.OrderCommonOffLine;
import com.mall.model.OrderGoods;
import com.mall.params.page.PageCondition;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {

    String generateOrder(String memberId, String receiverName, String phoneNum, String schoolName, Long schoolId, String dormName, Long dormId, String address, JSONArray cartIdList, String province, String city, String district,BigDecimal shipFee);

    String generateOfflineOrder(String memberId, String receiverName, String phoneNum, String schoolName, Long schoolId, String dormName, Long dormId, String address, JSONArray cartIdList, String province, String city, String district,BigDecimal shipFee);

    PageInfo<OrderCommon> orderListPage(PageCondition condition, Long status, String memberId);

    PageInfo<OrderCommonOffLine> offlineOrderListPage(PageCondition condition, Long status, String memberId);

    int cancelOrderBySn(String orderSn, String memberId);

    int cancelOfflineOrderBySn(String orderSn, String memberId);

    int finishOrderBySn(String orderSn, String memberId);

    int finishOfflineOrderBySn(String orderSn, String memberId);

    PageInfo<OrderCommon> userOrderListPage(PageCondition condition, Long status, String userId,int type);
    PageInfo<OrderCommonOffLine> userOfflineOrderListPage(PageCondition condition, Long status, String userId,int type);

    int updateOrderStatusBySn(String orderSn, int value);

    int updateOfflineOrderStatusBySn(String orderSn, int value);
    int finishOfflineOrderStatusBySn(String orderSn, int value);

    OrderCommon findByOrderSn(String orderSn);

    OrderCommonOffLine findByOfflineOrderSn(String orderSn);

    int updateOrderBySn(OrderCommon orderCommon);

    int updateOfflineOrderBySn(OrderCommonOffLine orderCommonOffLine);

    OrderCommon findByTradeNo(String out_trade_no);

    List<OrderCommon> findTimeOutOrders(Date timeOutDate);

    List<OrderGoods> findOrderGoodsByOrderSn(String orderSn);

    int updateOrderBySnAndShare(OrderCommon orderCommon);

    int updateOrderBySnAndShareRefund(OrderCommon orderCommon);

    int requestRefundOrderBySn(String orderSn, String memberId);

    int requestRefundOfflineOrderBySn(String orderSn, String memberId);

    OrderCommon findDetailByOrderSn(String orderSn);

    OrderCommonOffLine findOfflineDetailByOrderSn(String orderSn);

    PageInfo<OrderCommon> merchantOrderListPage(PageCondition condition, Integer status, String userId);

    PageInfo<OrderCommonOffLine> merchantOrderOfflineListPage(PageCondition condition, Integer status, String userId);

    PageInfo<OrderCommonOffLine> testOfflineOrderListPage(PageCondition condition);

    int setExpireOrder(String orderSn);
}
