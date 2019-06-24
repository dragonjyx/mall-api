package com.mall.webapi.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.java.response.JsonResult;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.params.status.OrderStatus;
import com.mall.params.status.UserType;
import com.mall.service.OrderService;
import com.mall.service.SchoolService;
import com.mall.webapi.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 供应商
 */
@Slf4j
@Controller
@RequestMapping("merchant")
public class MerchantController extends BaseController {


    @Autowired
    private OrderService orderService;

    /**
     * 我的订单列表，分页查询，订单状态，0：待支付，1：已支付(待配送)，2：已支付(配送中)，3：已支付（配送完成），4：订单完成， 5：已取消 6：已删除
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order-list",method = RequestMethod.POST)
    public JsonResult orderList(HttpServletRequest request, @RequestBody JSONObject params){
        log.warn("供货商线上订单查询:{}",params.toJSONString());
        Long status = params.getLong("status");

        Integer pageSize    = params.getInteger("pageSize");
        Integer currentPage = params.getInteger("currentPage");
        if(currentPage == null){
            currentPage = 0;
        }
        if(pageSize == null){
            pageSize = 20;
        }
        PageCondition condition = new PageCondition();
        condition.setCurrentPage(currentPage);
        condition.setPageSize(pageSize);

        String token   = request.getParameter("token");
        String userId  = getUserId(token);
        log.warn("供货商ID：{}",userId);

        PageInfo<OrderCommon> orderCommonPageInfo = orderService.userOrderListPage(condition,status,userId,UserType.SUPPLIER.value);
        return JsonResult.success(orderCommonPageInfo);
    }



    /**
     * 我的订单列表，分页查询，订单状态，0：待支付，1：已支付(待配送)，2：已支付(配送中)，3：已支付（配送完成），4：订单完成， 5：已取消 6：已删除
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order-list",method = RequestMethod.POST)
    public JsonResult offlineOrderList(HttpServletRequest request, @RequestBody JSONObject params){
        log.warn("供货商线下订单查询:{}",params.toJSONString());
        Long status = params.getLong("status");

        Integer pageSize    = params.getInteger("pageSize");
        Integer currentPage = params.getInteger("currentPage");
        if(currentPage == null){
            currentPage = 0;
        }
        if(pageSize == null){
            pageSize = 20;
        }
        PageCondition condition = new PageCondition();
        condition.setCurrentPage(currentPage);
        condition.setPageSize(pageSize);

        String token   = request.getParameter("token");
        String userId  = getUserId(token);
        log.warn("供货商ID：{}",userId);

        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = orderService.userOfflineOrderListPage(condition,status,userId,UserType.SUPPLIER.value);
        log.warn("orderCommonPageInfo:{}",orderCommonOffLinePageInfo.toString());
        return JsonResult.success(orderCommonOffLinePageInfo);
    }



    @ResponseBody
    @RequestMapping(value = "test/offline-order-list",method = RequestMethod.POST)
    public JsonResult testOfflineOrderList(HttpServletRequest request, @RequestBody JSONObject params){
        log.warn("供货商线下订单查询:{}",params.toJSONString());
        Long status = params.getLong("status");

        Integer pageSize    = params.getInteger("pageSize");
        Integer currentPage = params.getInteger("currentPage");
        if(currentPage == null){
            currentPage = 0;
        }
        if(pageSize == null){
            pageSize = 20;
        }
        PageCondition condition = new PageCondition();
        condition.setCurrentPage(currentPage);
        condition.setPageSize(pageSize);

        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = orderService.testOfflineOrderListPage(condition);
        log.warn("orderCommonPageInfo:{}",orderCommonOffLinePageInfo.toString());
        return JsonResult.success(orderCommonOffLinePageInfo);
    }



}
