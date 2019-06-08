package com.mall.webapi.controller.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.java.response.JsonResult;
import com.java.validate.ServiceException;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.params.resp.SchoolResp;
import com.mall.params.status.OrderStatus;
import com.mall.service.*;
import com.mall.webapi.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员信息
 */
@Slf4j
@Controller
@RequestMapping("member")
public class MemberController extends BaseController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private MallAddressService mallAddressService;

    @Autowired
    private MallCartService mallCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeChatService weChatService;

    /**
     * 查询收货地址(小区自提)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "schoolDrom/query",method = RequestMethod.POST)
    public JsonResult querySchoolDrom(HttpServletRequest request,@RequestBody JSONObject params){
        log.warn("params:{}",params.toJSONString());
        Long schoolId = params.getLong("schoolId");
        if(schoolId == null){
            return JsonResult.fail("schoolId is null");
        }
        List<SchoolDorm> schoolDormList = schoolService.findBySchoolId(schoolId);
        if(schoolDormList.isEmpty()){
            return null;
        }
        return JsonResult.success(schoolDormList.get(0));
    }



    @ResponseBody
    @RequestMapping(value = "default-location/query",method = RequestMethod.GET)
    public JsonResult queryDefaultLocation(HttpServletRequest request){
        String token  = request.getParameter("token");
        String openId = getOpenId(token);
        MemberLocation memberLocation = memberService.findMemberLocationByOpenId(openId);
        if(memberLocation == null){
            return JsonResult.fail("没有设定默认定位");
        }
        return JsonResult.success(memberLocation);
    }


    /**
     * 定位查询 (小区/校园-楼房/宿舍) 树形结构
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "location-list/query/{type}",method = RequestMethod.GET)
    public JsonResult locationListQuery(HttpServletRequest request,@PathVariable("type")Integer type){
        List<SchoolResp> schoolTreeList  =schoolService.findSchoolTreeList(type);
        return JsonResult.success(schoolTreeList);
    }


    /**
     * 设置默认定位
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "default-location/set",method = RequestMethod.POST)
    public JsonResult setMyDefaultLocation(HttpServletRequest request, @RequestBody MemberLocation memberLocation){
        String token  = request.getParameter("token");
        String openId = getOpenId(token);

        log.warn("token:{},openId:{}",token,openId);
        if(StringUtils.isEmpty(memberLocation.getLocationId())){
            return JsonResult.fail("位置id不能为空");
        }

        if(StringUtils.isEmpty(memberLocation.getLocationName())){
            return JsonResult.fail("位置名称不能为空");
        }


        try {
            JSONObject.parseArray(memberLocation.getLocationId());
        }catch (Exception e){
            return JsonResult.fail("位置id格式不正确，请填写正确的json数组格式");
        }


        try {
            JSONObject.parseArray(memberLocation.getLocationName());
        }catch (Exception e){
            return JsonResult.fail("位置名称格式不正确，请填写正确的json数组格式");
        }

        memberLocation.setOpenId(openId);

        int result = memberService.setMemberLocation(memberLocation);
        if(result != 1){
            return JsonResult.fail("设定默认定位失败");
        }
        return JsonResult.success("设定默认定位成功");
    }


    /**
     * 查询购物车列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cart-list/query",method = RequestMethod.GET)
    public JsonResult queryCartList(HttpServletRequest request){
        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();
        List<MallCart> mallCarts = mallCartService.findMallCatByUid(memberId);
        return JsonResult.success(mallCarts);
    }




    /**
     * 加入购物车
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cart/add",method = RequestMethod.POST)
    public JsonResult add2Cart(HttpServletRequest request,@RequestBody MallCart cart){
        log.warn("添加购物车:{}",cart.toString());
        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();
        cart.setUid(memberId);

        int result = mallCartService.addMallCart(cart);
        if(result != 1){
            return JsonResult.fail("加入购物车失败");
        }
        return JsonResult.success("加入购物车成功");
    }


    /**
     * 删除购物车
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cart/delete/{id}",method = RequestMethod.GET)
    public JsonResult deleteCart(HttpServletRequest request,@PathVariable("id") long id){
        int result = mallCartService.deleteCart(id);
        if(result != 1){
            return JsonResult.fail("删除购物车失败");
        }
        return JsonResult.success("删除购物车成功");
    }

    /**
     * 批量删除购物车
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cart/delete-all",method = RequestMethod.GET)
    public JsonResult deleteMyCart(HttpServletRequest request){
        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        int result = mallCartService.deleteAllCart(memberId);
        if(result <0 ){
            return JsonResult.fail("删除购物车失败");
        }
        return JsonResult.success("删除购物车成功");
    }


    /**
     * 设置购物车数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cart/set-num",method = RequestMethod.POST)
    public JsonResult setCartGoodsNum(HttpServletRequest request,@RequestBody JSONObject params){
        Long id = params.getLong("id");
        Integer number = params.getInteger("number");

        if(id == null){
            return JsonResult.fail("id is null");
        }

        if(number == null){
            return JsonResult.fail("number is null");
        }


        if(number == 0){//删除购物车
            int result = mallCartService.deleteCart(id);
            if(result != 1){
                return JsonResult.fail("删除购物车失败");
            }
            return JsonResult.success("删除购物车成功");
        }

        int result = mallCartService.setCartGoodsNum(id,number);
        if(result != 1){
            return JsonResult.fail("修改购物车商品数量失败");
        }
        return JsonResult.success("修改购物车商品数量成功");
    }







    /**
     * 生成订单：提交用户信息（手机号，用户名），配送（详细）地址（学校，宿舍），勾选购物车的商品列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order/generate",method = RequestMethod.POST)
    public JsonResult generateOrder(HttpServletRequest request,@RequestBody JSONObject params){
        String province = params.getString("province");
        String city     = params.getString("city");
        String district = params.getString("district");
        String receiverName = params.getString("receiverName");
        String phoneNum   = params.getString("phoneNum");
        String schoolName = params.getString("schoolName");
        Long schoolId     = params.getLong("schoolId");
        String dormName   = params.getString("dormName");
        Long dormId       = params.getLong("dormId");
        String address    = params.getString("address");
        BigDecimal shipFee   = params.getBigDecimal("shipFee");
        JSONArray cartIdList = params.getJSONArray("cartIds");

        log.warn("订单生成:{}",params.toJSONString());

        if(StringUtils.isEmpty(receiverName)){
            return JsonResult.fail("请填写收货人名称");
        }
        if(StringUtils.isEmpty(phoneNum)){
            return JsonResult.fail("请填写手机号码");
        }
        if(StringUtils.isEmpty(schoolName)){
            return JsonResult.fail("请选择学校/小区");
        }
        if(schoolId == null){
            return JsonResult.fail("请选择学校/小区");
        }
        if(StringUtils.isEmpty(dormName)){
            return JsonResult.fail("请选择楼号");
        }
        if(dormId == null){
            return JsonResult.fail("请选择楼号");
        }
        if(StringUtils.isEmpty(address)){
            return JsonResult.fail("请填写收货地址");
        }
        if(cartIdList.isEmpty()){
            return JsonResult.fail("购物车商品为空");
        }

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();
        log.warn("下单会员ID：{}",memberId);

        try {
            String orderSn = orderService.generateOrder(memberId,receiverName,phoneNum,schoolName,schoolId,dormName,dormId,address,cartIdList,province,city,district,shipFee);
            if(orderSn == null){
                return JsonResult.fail("订单生成失败");
            }
            return JsonResult.success(orderSn);
        }catch (ServiceException e){
            log.error("e:",e);
            return JsonResult.fail(e.getMessage());
        }

    }




    /**
     * (线下)生成订单：提交用户信息（手机号，用户名），配送（详细）地址（学校，宿舍），勾选购物车的商品列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order/generate",method = RequestMethod.POST)
    public JsonResult generateOfflineOrder(HttpServletRequest request,@RequestBody JSONObject params){
        String province = params.getString("province");
        String city     = params.getString("city");
        String district = params.getString("district");
        String receiverName = params.getString("receiverName");
        String phoneNum   = params.getString("phoneNum");
        String schoolName = params.getString("schoolName");
        Long schoolId     = params.getLong("schoolId");
        String dormName   = params.getString("dormName");
        Long dormId       = params.getLong("dormId");
        String address    = params.getString("address");
        BigDecimal shipFee   = params.getBigDecimal("shipFee");
        JSONArray cartIdList = params.getJSONArray("cartIds");


        if(StringUtils.isEmpty(receiverName)){
            return JsonResult.fail("请填写收货人名称");
        }
        if(StringUtils.isEmpty(phoneNum)){
            return JsonResult.fail("请填写手机号码");
        }
        if(StringUtils.isEmpty(schoolName)){
            return JsonResult.fail("请选择学校/小区");
        }
        if(schoolId == null){
            return JsonResult.fail("请选择学校/小区");
        }
        if(StringUtils.isEmpty(dormName)){
            return JsonResult.fail("请选择楼号");
        }
        if(dormId == null){
            return JsonResult.fail("请选择楼号");
        }
        if(StringUtils.isEmpty(address)){
            return JsonResult.fail("请填写收货地址");
        }
        if(cartIdList.isEmpty()){
            return JsonResult.fail("购物车商品为空");
        }

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();
        log.warn("下单会员ID：{}",memberId);

        try {
            String orderSn = orderService.generateOfflineOrder(memberId,receiverName,phoneNum,schoolName,schoolId,dormName,dormId,address,cartIdList,province,city,district,shipFee);
            if(orderSn == null){
                return JsonResult.fail("订单生成失败");
            }

            //通知楼长，配送员
            OrderCommonOffLine orderCommonOffLine = orderService.findByOfflineOrderSn(orderSn);
            weChatService.notify2send(orderCommonOffLine);


            return JsonResult.success(orderSn);
        }catch (ServiceException e){
            return JsonResult.fail(e.getMessage());
        }

    }


    /**
     * 我的收货地址列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "address-list/query",method = RequestMethod.GET)
    public JsonResult myAddressList(HttpServletRequest request){
        String token    = request.getParameter("token");
        String openId   = getOpenId(token);
        Member member   = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();
        List<MallAddress> mallAddresseList = mallAddressService.findAllAddressByUid(memberId);
        return JsonResult.success(mallAddresseList);
    }



    /**
     * 添加我的收货地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "address/add",method = RequestMethod.POST)
    public JsonResult addNewAddress(HttpServletRequest request,@RequestBody MallAddress mallAddress){
        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        if(StringUtils.isEmpty(mallAddress.getSchoolName())){
            return JsonResult.fail("请选择学校/小区名称");
        }

        if(StringUtils.isEmpty(mallAddress.getDormName())){
            return JsonResult.fail("请填选择舍楼名称");
        }

        if(StringUtils.isEmpty(mallAddress.getAddress())){
            return JsonResult.fail("请填写详细地址");
        }

        if(StringUtils.isEmpty(mallAddress.getPhone())){
            return JsonResult.fail("请填写手机号码");
        }

        if(mallAddress.getSchoolId() == null){
            return JsonResult.fail("schoolId is null");
        }
        if(mallAddress.getSchoolDormId() == null){
            return JsonResult.fail("schoolDormId is null");
        }
        if(mallAddress.getType() == null){
            return JsonResult.fail("type is null");
        }
        if(StringUtils.isEmpty(mallAddress.getReceiverName())){
            return JsonResult.fail("请填写收货人");
        }

        mallAddress.setUid(memberId);
        int result = mallAddressService.add(mallAddress);
        if(result != 1){
            return JsonResult.fail("添加地址失败");
        }

        return JsonResult.success("添加地址成功");
    }


    /**
     * 更新我的收货地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "address/update",method = RequestMethod.POST)
    public JsonResult updateAddress(HttpServletRequest request,@RequestBody MallAddress mallAddress){
        if(StringUtils.isEmpty(mallAddress.getSchoolName())){
            return JsonResult.fail("请选择学校/小区名称");
        }
        if(StringUtils.isEmpty(mallAddress.getDormName())){
            return JsonResult.fail("请填选择舍楼名称");
        }
        if(StringUtils.isEmpty(mallAddress.getAddress())){
            return JsonResult.fail("请填写详细地址");
        }
        if(StringUtils.isEmpty(mallAddress.getAddressSn())){
            return JsonResult.fail("addressSn is null");
        }
        if(mallAddress.getSchoolId() == null){
            return JsonResult.fail("schoolId is null");
        }
        if(mallAddress.getSchoolDormId() == null){
            return JsonResult.fail("schoolDormId is null");
        }
        if(mallAddress.getType() == null){
            return JsonResult.fail("type is null");
        }

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();
        mallAddress.setUid(memberId);

        int result = mallAddressService.update(mallAddress);
        if(result != 1){
            return JsonResult.fail("更新地址失败");
        }

        return JsonResult.success("更新地址成功");
    }



    /**
     * 删除我的收货地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "address/delete/{addressSn}",method = RequestMethod.GET)
    public JsonResult addNewAddress(HttpServletRequest request, @PathVariable("addressSn")String addressSn){
        int result = mallAddressService.delete(addressSn);
        if(result != 1){
            return JsonResult.fail("删除地址失败");
        }
        return JsonResult.success("删除成功");
    }



    /**
     * 查询我的收货地址根据id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "address/query/{addressSn}",method = RequestMethod.GET)
    public JsonResult findMyAddress(HttpServletRequest request, @PathVariable("addressSn")String addressSn){
        MallAddress mallAddress = mallAddressService.findByAddressSn(addressSn);
        if(mallAddress == null){
            return JsonResult.fail("查询地址失败");
        }
        return JsonResult.success(mallAddress);
    }



    /**
     * 查询我的默认收货地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "address/query-default",method = RequestMethod.GET)
    public JsonResult findMyDefaultAddress(HttpServletRequest request){
        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();


        MallAddress mallAddress = mallAddressService.findMyDefaultByAddressSn(memberId);
        if(mallAddress == null){
            return JsonResult.fail("查询地址失败");
        }
        return JsonResult.success(mallAddress);
    }



    /**
     * 设置我的默认收货地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "address/set-default",method = RequestMethod.POST)
    public JsonResult setDefaultAddress(HttpServletRequest request,@RequestBody JSONObject params){
        String addressSn  = params.getString("addressSn");
        Integer isDefault = params.getInteger("isDefault");

        if(StringUtils.isEmpty(addressSn)){
            return JsonResult.fail("addressSn is null");
        }

        if(isDefault == null){
            return JsonResult.fail("isDefault is null");
        }

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        int result = mallAddressService.setDefaultAddress(memberId,addressSn,isDefault);
        if(result != 1){
            return JsonResult.fail("设置默认地址失败");
        }
        return JsonResult.success("设置默认地址成功");
    }



    /**
     * 我的订单列表，分页查询，订单状态：0：待支付，1：已支付(待配送)，2：已支付(配送中)，3：已完成，4：已取消
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order-list",method = RequestMethod.POST)
    public JsonResult orderList(HttpServletRequest request, @RequestBody JSONObject params){
        log.warn("在线订单列表查询params:{}",params);
        Long status = params.getLong("status");

        Integer pageSize = params.getInteger("pageSize");
        Integer currentPage = params.getInteger("currentPage");
        if(currentPage == null){
            currentPage = 0;
        }
        if(pageSize == null){
            pageSize = 20;
        }
        PageCondition condition = new PageCondition();
        condition.setCurrentPage(currentPage  );
        condition.setPageSize(pageSize);

        String token    = request.getParameter("token");
        log.warn("----------------token:{}",token);
        String openId   = getOpenId(token);
        log.warn("----------------openId:{}",openId);
        Member member   = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        PageInfo<OrderCommon> orderCommonPageInfo = orderService.orderListPage(condition,status,memberId);
        return JsonResult.success(orderCommonPageInfo);
    }

    /**
     * 我的订单列表，分页查询，订单状态：0：待支付，1：已支付(待配送)，2：已支付(配送中)，3：已完成，4：已取消
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order-list",method = RequestMethod.POST)
    public JsonResult offlineOrderList(HttpServletRequest request, @RequestBody JSONObject params){
        log.warn("线下订单列表查询params:{}",params);
        Long status = params.getLong("status");
        Integer pageSize = params.getInteger("pageSize");
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

        String token    = request.getParameter("token");
        String openId   = getOpenId(token);
        Member member   = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        PageInfo<OrderCommonOffLine> offloneOrderCommonPageInfo = orderService.offlineOrderListPage(condition,status,memberId);
        return JsonResult.success(offloneOrderCommonPageInfo);
    }


    /**
     * 取消订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order/cancel/{orderSn}",method = RequestMethod.GET)
    public JsonResult cancelOrder(HttpServletRequest request,@PathVariable("orderSn")String orderSn){

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        OrderCommon orderCommon = orderService.findByOrderSn(orderSn);
        if(orderCommon == null){
            return JsonResult.fail("订单异常");
        }

        if(orderCommon.getStatus() == OrderStatus.PAYED_DELIVING.value ||
                orderCommon.getStatus() == OrderStatus.PAYED_NO_DELIVER.value ||
                orderCommon.getStatus() == OrderStatus.PAYED_FINISH_DELIVER.value ||
                orderCommon.getStatus() == OrderStatus.ORDER_FINISH.value){
            return JsonResult.fail("订单已经支付，请申请退款");
        }


        int result = orderService.cancelOrderBySn(orderSn,memberId);
        if(result != 1){
            return JsonResult.fail("取消订单失败");
        }

        //发送消息给管理员

        return JsonResult.success("取消订单成功");
    }

    /**
     * 取消订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order/cancel/{orderSn}",method = RequestMethod.GET)
    public JsonResult offlineCancelOrder(HttpServletRequest request,@PathVariable("orderSn")String orderSn){

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        OrderCommonOffLine orderCommon = orderService.findByOfflineOrderSn(orderSn);
        if(orderCommon == null){
            return JsonResult.fail("订单异常");
        }

        if(orderCommon.getStatus() == OrderStatus.PAYED_DELIVING.value ||
                orderCommon.getStatus() == OrderStatus.PAYED_NO_DELIVER.value ||
                orderCommon.getStatus() == OrderStatus.PAYED_FINISH_DELIVER.value ||
                orderCommon.getStatus() == OrderStatus.ORDER_FINISH.value){
            return JsonResult.fail("订单已经支付，请申请退款");
        }


        int result = orderService.cancelOfflineOrderBySn(orderSn,memberId);
        if(result != 1){
            return JsonResult.fail("取消订单失败");
        }

        //发送消息给管理员


        return JsonResult.success("取消订单成功");
    }



    /**
     * 确认订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order/finish/{orderSn}",method = RequestMethod.GET)
    public JsonResult finishOrder(HttpServletRequest request,@PathVariable("orderSn")String orderSn){

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        OrderCommon orderCommon = orderService.findByOrderSn(orderSn);
        if(orderCommon == null){
            return JsonResult.fail("订单异常");
        }

        if(orderCommon.getStatus() == OrderStatus.CANCEL.value){
            return JsonResult.fail("订单已取消");
        }

        if(orderCommon.getStatus() == OrderStatus.NO_PAY.value){
            return JsonResult.fail("订单未支付");
        }

        int result = orderService.finishOrderBySn(orderSn,memberId);
        if(result != 1){
            return JsonResult.fail("确认订单失败");
        }
        return JsonResult.success("确认订单成功");
    }



    /**
     * 确认订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order/finish/{orderSn}",method = RequestMethod.GET)
    public JsonResult offlineFinishOrder(HttpServletRequest request,@PathVariable("orderSn")String orderSn){

        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        OrderCommonOffLine orderCommonOffLine = orderService.findByOfflineOrderSn(orderSn);
        if(orderCommonOffLine == null){
            return JsonResult.fail("订单异常");
        }

        if(orderCommonOffLine.getStatus() == OrderStatus.CANCEL.value){
            return JsonResult.fail("订单已取消");
        }

        if(orderCommonOffLine.getStatus() == OrderStatus.NO_PAY.value){
            return JsonResult.fail("订单未支付");
        }

        int result = orderService.finishOfflineOrderBySn(orderSn,memberId);
        if(result != 1){
            return JsonResult.fail("确认订单失败");
        }
        return JsonResult.success("确认订单成功");
    }



    /**
     * 删除订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order/delete/{orderSn}",method = RequestMethod.GET)
    public JsonResult deleteOrder(HttpServletRequest request,@PathVariable("orderSn")String orderSn){

        OrderCommon orderCommon = orderService.findByOrderSn(orderSn);
        if(orderCommon == null){
            return JsonResult.fail("订单异常");
        }
        orderCommon.setIsDelete(1);
        int result = orderService.updateOrderBySn(orderCommon);
        if(result != 1){
            return JsonResult.fail("删除订单失败");
        }
        return JsonResult.success("删除订单成功");
    }


    /**
     * 删除订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order/delete/{orderSn}",method = RequestMethod.GET)
    public JsonResult deleteOfflineOrder(HttpServletRequest request,@PathVariable("orderSn")String orderSn){

        OrderCommonOffLine orderCommonOffLine = orderService.findByOfflineOrderSn(orderSn);
        if(orderCommonOffLine == null){
            return JsonResult.fail("订单异常");
        }
        orderCommonOffLine.setIsDelete(1);
        int result = orderService.updateOfflineOrderBySn(orderCommonOffLine);
        if(result != 1){
            return JsonResult.fail("删除订单失败");
        }
        return JsonResult.success("删除订单成功");
    }

    /**
     * 申请退款
     * @param request
     * @param orderSn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order/refund-request/{orderSn}",method = RequestMethod.GET)
    public JsonResult requestRefundRequest(HttpServletRequest request,@PathVariable("orderSn")String orderSn){
        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        OrderCommon orderCommon = orderService.findByOrderSn(orderSn);
        if(orderCommon == null){
            return JsonResult.fail("订单异常");
        }
        if(orderCommon.getStatus() == OrderStatus.CANCEL.value){
            return JsonResult.fail("订单已取消");
        }
        if(orderCommon.getStatus() == OrderStatus.NO_PAY.value){
            return JsonResult.fail("订单未支付");
        }

        int result = orderService.requestRefundOrderBySn(orderSn,memberId);
        if(result != 1){
            return JsonResult.fail("提交申请失败");
        }
        return JsonResult.success("提交申请成功");
    }

    /**
     * 申请退款
     * @param request
     * @param orderSn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order/refund-request/{orderSn}",method = RequestMethod.GET)
    public JsonResult requestOfflineRefundRequest(HttpServletRequest request,@PathVariable("orderSn")String orderSn){
        String token = request.getParameter("token");
        String openId = getOpenId(token);
        Member member = memberService.findByOpenId(openId);
        if(member == null){
            return JsonResult.fail("token 已失效");
        }
        String memberId = member.getMemberId();

        OrderCommonOffLine orderCommonOffLine = orderService.findByOfflineOrderSn(orderSn);
        if(orderCommonOffLine == null){
            return JsonResult.fail("订单异常");
        }
        if(orderCommonOffLine.getStatus() == OrderStatus.CANCEL.value){
            return JsonResult.fail("订单已取消");
        }
        if(orderCommonOffLine.getStatus() == OrderStatus.NO_PAY.value){
            return JsonResult.fail("订单未支付");
        }

        int result = orderService.requestRefundOfflineOrderBySn(orderSn,memberId);
        if(result != 1){
            return JsonResult.fail("提交申请失败");
        }
        return JsonResult.success("提交申请成功");
    }



    /**
     * 订单详情
     * @param request
     * @param orderSn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order-detail/{orderSn}",method = RequestMethod.GET)
    public JsonResult orderDetail(HttpServletRequest request,@PathVariable("orderSn")String orderSn){
        OrderCommon orderCommon = orderService.findDetailByOrderSn(orderSn);
        return JsonResult.success(orderCommon);
    }

    /**
     * 离线订单详情
     * @param request
     * @param orderSn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-order-detail/{orderSn}",method = RequestMethod.GET)
    public JsonResult offlineOrderDetail(HttpServletRequest request,@PathVariable("orderSn")String orderSn){
        OrderCommonOffLine orderCommonOffLine = orderService.findOfflineDetailByOrderSn(orderSn);
        return JsonResult.success(orderCommonOffLine);
    }


}
