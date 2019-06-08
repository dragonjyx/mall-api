package com.mall.webapi.controller.agent;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.java.response.JsonResult;
import com.mall.model.OrderCommon;
import com.mall.model.OrderCommonOffLine;
import com.mall.model.UserSchoolDorm;
import com.mall.model.UserSchoolDormManage;
import com.mall.params.page.PageCondition;
import com.mall.params.status.OrderStatus;
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
 * 代理商 楼长
 */
@Slf4j
@Controller
@RequestMapping("agent")
public class RegionManagerController extends BaseController {


    @Autowired
    private SchoolService schoolService;

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

        List<Object> dormIdList = new ArrayList<Object>();
        List<UserSchoolDorm> userSchoolDormList = schoolService.findByUserSchoolDorm(userId);
        for(UserSchoolDorm userSchoolDorm:userSchoolDormList ){
            dormIdList.add(userSchoolDorm.getDormId());
        }

        PageInfo<OrderCommon> orderCommonPageInfo = orderService.userOrderListPage(condition,status,dormIdList);
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

        List<Object> dormIdList = new ArrayList<Object>();
        List<UserSchoolDorm> userSchoolDormList = schoolService.findByUserSchoolDorm(userId);
        for(UserSchoolDorm userSchoolDorm:userSchoolDormList ){
            dormIdList.add(userSchoolDorm.getDormId());
        }

        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = orderService.userOfflineOrderListPage(condition,status,dormIdList);
        return JsonResult.success(orderCommonOffLinePageInfo);
    }



    /**
     * 更新订单状态：配送完成
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order-status/finish",method = RequestMethod.POST)
    public JsonResult updateOrderStatus(HttpServletRequest request,@RequestBody JSONObject params){
        String orderSn = params.getString("orderSn");
        Integer status = params.getInteger("status");

        if(StringUtils.isEmpty(orderSn)){
            return JsonResult.fail("orderSn is null");
        }
        if(status == null){
            return JsonResult.fail("status is null");
        }

        if(status != OrderStatus.PAYED_FINISH_DELIVER.value){
            return JsonResult.fail("status is must 3");
        }


        OrderCommon orderCommon = orderService.findByOrderSn(orderSn);
        if(orderCommon == null){
            return JsonResult.fail("订单异常");
        }
        UserSchoolDormManage userSchoolDormManage = schoolService.findByDormId(orderCommon.getDormId());
        if(userSchoolDormManage != null &&
                orderCommon.getStatus() != OrderStatus.PAYED_DELIVING.value &&
                status == OrderStatus.PAYED_FINISH_DELIVER.value){//有配送员 并且还没配送
            return JsonResult.fail("等待配送员配送");
        }

        String token   = request.getParameter("token");
        String userId = getUserId(token);

        UserSchoolDorm userSchoolDorm = schoolService.findByUserIdAndDromId(userId,orderCommon.getDormId());
        if(userSchoolDorm == null){
            return JsonResult.fail("订单不属于该代理商");
        }

        int result = orderService.updateOrderStatusBySn(orderSn,OrderStatus.PAYED_FINISH_DELIVER.value);
        if(result != 1){
            return JsonResult.fail("更改订单状态失败");
        }
        return JsonResult.success("更改订单状态成功");
    }



    /**
     * 更新订单状态：配送完成
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order-status/finish",method = RequestMethod.POST)
    public JsonResult updateOfflineOrderStatus(HttpServletRequest request,@RequestBody JSONObject params){
        log.warn("线下配送完成，进行分润,{}",params.toJSONString());
        String orderSn = params.getString("orderSn");
        Integer status = params.getInteger("status");

        if(StringUtils.isEmpty(orderSn)){
            return JsonResult.fail("orderSn is null");
        }
        if(status == null){
            return JsonResult.fail("status is null");
        }

        if(status != OrderStatus.PAYED_FINISH_DELIVER.value){
            return JsonResult.fail("status is must 3");
        }


        OrderCommonOffLine orderCommonOffLine = orderService.findOfflineDetailByOrderSn(orderSn);
        if(orderCommonOffLine == null){
            return JsonResult.fail("订单异常");
        }
        UserSchoolDormManage userSchoolDormManage = schoolService.findByDormId(orderCommonOffLine.getDormId());
        if(userSchoolDormManage != null &&
                orderCommonOffLine.getStatus() != OrderStatus.PAYED_DELIVING.value &&
                status == OrderStatus.PAYED_FINISH_DELIVER.value){//有配送员 并且还没配送
            return JsonResult.fail("等待配送员配送");
        }

        String token  = request.getParameter("token");
        String userId = getUserId(token);

        UserSchoolDorm userSchoolDorm = schoolService.findByUserIdAndDromId(userId,orderCommonOffLine.getDormId());
        if(userSchoolDorm == null){
            return JsonResult.fail("订单不属于该代理商");
        }

        int result = orderService.finishOfflineOrderStatusBySn(orderSn,OrderStatus.PAYED_FINISH_DELIVER.value);//完成，并且分润
        if(result != 1){
            return JsonResult.fail("更改订单状态失败");
        }
        return JsonResult.success("更改订单状态成功");
    }





}
