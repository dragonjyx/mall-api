package com.mall.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.utils.date.DateUtils;
import com.java.validate.ServiceException;
import com.mall.dao.*;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.params.status.*;
import com.mall.service.OrderService;
import com.mall.service.utils.SnUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderCommonDao orderCommonDao;

    @Autowired
    private OrderCommonOffLineDao orderCommonOffLineDao;

    @Autowired
    private MallCartDao mallCartDao;

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Autowired
    private MallGoodsDao mallGoodsDao;


    @Transactional
    @Override
    public String generateOrder(String memberId, String receiverName, String phoneNum, String schoolName, Long schoolId, String dormName, Long dormId, String address, JSONArray cartIdList, String province, String city, String district,BigDecimal shipFee) {
        if(shipFee == null){
            shipFee = new BigDecimal(0);
        }

        //1.生成总订单
        Date now = new Date();
        String orderSn = SnUtil.createOrderSn();

        OrderCommon orderCommon = new OrderCommon();
        orderCommon.setOrderSn(orderSn);
        orderCommon.setMemberId(memberId);
        orderCommon.setCreateTime(now);
        orderCommon.setSchoolName(schoolName);
        orderCommon.setSchoolId(schoolId);
        orderCommon.setDormName(dormName);
        orderCommon.setDormId(dormId);
        orderCommon.setAddress(address);
        orderCommon.setPhoneNum(phoneNum);
        orderCommon.setReceiverName(receiverName);
        orderCommon.setProvince(province);
        orderCommon.setCity(city);
        orderCommon.setDistrict(district);
        orderCommon.setShipFee(shipFee);//运费
        orderCommon.setIsDelete(0);


//        List<MallGoods> mallGoodsList = new ArrayList<MallGoods>();
        BigDecimal totalCostPrice = new BigDecimal(0);

        List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
        OrderGoods orderGoods = null;
        //计算总价格
        List<MallCart> mallCartList = new ArrayList<MallCart>();
        //查询购物车所有商品总价格
        log.warn("查询购物车所有商品总价格");
        MallCart cart = null;
        for(int i = 0 ; i < cartIdList.size() ; i++){
            long cartId = cartIdList.getLong(i);
            cart = mallCartDao.findById(cartId);
            if(cart == null){
                log.error("购物车异常");
                throw new ServiceException("购物车异常");
            }
            mallCartList.add(cart);

            orderGoods = new OrderGoods();
            orderGoods.setOrderSn(orderSn);
            orderGoods.setGoodsSn(cart.getGoodsSn());
            orderGoods.setGoodsName(cart.getGoodsName());
            orderGoods.setGoodsCode(cart.getGoodsCode());
            orderGoods.setGoodsImage(cart.getGoodsImage());
            orderGoods.setNum(cart.getBuyNum());
            orderGoods.setMarketPrice(cart.getMarketPrice());
            orderGoods.setPrice(cart.getGoodsPrice());
            orderGoods.setSpecValue(cart.getSpecValue());
            orderGoods.setShipType(ShipType.SONG_HUO_SHANG_MEN.value);
            orderGoods.setShipNum(cart.getBuyNum());

            //减库存
            MallGoods mallGoods = mallGoodsDao.findByGoodsSnAndGoodsCode(cart.getGoodsSn(),cart.getGoodsCode());
            if(mallGoods != null){
//                log.warn("获取商品信息:{}",JSONObject.toJSONString(mallGoods));
                BigDecimal costPrice = mallGoods.getCostPrice();
                orderGoods.setCostPrice(costPrice);//成本价
                orderGoods.setSpecValue(mallGoods.getSpecValues());//规格值

                Integer stockNum = mallGoods.getStockNum();
                Integer buyNum   = cart.getBuyNum();

                totalCostPrice = totalCostPrice.add(costPrice.multiply(new BigDecimal(buyNum)));//成本价 累加

                stockNum = stockNum - buyNum;

                if(stockNum < 0 ){
                    log.error(mallGoods.getName() + "库存不足");
                    throw new ServiceException(mallGoods.getName() + "库存不足");
                }
//                mallGoodsList.add(mallGoods);

                mallGoods.setStockNum(stockNum);
                int result = mallGoodsDao.updateByCode(mallGoods);
                if(result != 1){
                    log.error(mallGoods.getName() + "库存减少，更新失败");
                    throw new ServiceException(mallGoods.getName() + "库存减少，更新失败");
                }
            }
            orderGoodsList.add(orderGoods);
        }

        log.warn("购物车商品:{}",mallCartList.toString());

        BigDecimal totalPrice = new BigDecimal(0);//计算订单总价
        for (MallCart mallCart:mallCartList){
            Integer buyNum = mallCart.getBuyNum();
            BigDecimal goodsPrice = mallCart.getGoodsPrice();
            if(goodsPrice == null){
                throw new ServiceException("购物车商品价格为空");
            }
            BigDecimal allGoodsPrice = goodsPrice.multiply(new BigDecimal(buyNum));//商品价格 * 数量
            totalPrice = totalPrice.add(allGoodsPrice);//统计总价格
        }
        log.warn("计算订单总价:{}",totalPrice);
        orderCommon.setAmount(totalPrice);
        BigDecimal orderAmount = totalPrice.add(shipFee);
        orderCommon.setOrderAmount(orderAmount);
        orderCommon.setStatus(OrderStatus.NO_PAY.value);//待支付
        orderCommon.setRefundStatus(RefundStatus.NORMAL.value);
        orderCommon.setPayType(PayType.WECHAT.value);


        //---------------计算分润 start --------------------
        //1、配送员
        UserSchoolDormManage userSchoolDormManage = userSchoolDormManageDao.findByDormId(dormId);
        if(userSchoolDormManage != null){
            orderCommon.setDeliverUserId(userSchoolDormManage.getUserId());
            orderCommon.setDeliverShare(shipFee);
        }else{
            log.error("xxxxxxxxxxxxxxxxx订单无配送员xxxxxxxxxxxxxxxxxxx");
        }
        //2、供应商
        UserSchoolDormSupplier userSchoolDormSupplier = userSchoolDormSupplierDao.findBySchoolId(schoolId);
        if(userSchoolDormSupplier != null){
            orderCommon.setMerchantShare(totalCostPrice);
            orderCommon.setMerchantUserId(userSchoolDormSupplier.getUserId());
        }else{
            log.error("xxxxxxxxxxxxxxxxx订单无供应商xxxxxxxxxxxxxxxxxxx");
        }
        //3.楼长分润
        UserSchoolDorm userSchoolDorm = userSchoolDormDao.findByDormId(dormId);
        if(userSchoolDorm != null){
            String userId = userSchoolDorm.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);
            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
                throw new ServiceException("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
            }

            BigDecimal shareAmount = totalPrice.subtract(totalCostPrice);//可分润金额
            BigDecimal userAmount  = shareAmount.multiply(userInfo.getDistributionRatio());//代理商分润
            orderCommon.setRegionUserId(userId);
            orderCommon.setDistributionRatio(userInfo.getDistributionRatio());
            orderCommon.setRegionShare(userAmount);
        }else{
            log.error("xxxxxxxxxxxxxxxxx订单无楼长xxxxxxxxxxxxxxxxxxx");
        }

        //---------------计算分润 end --------------------


        int result = orderCommonDao.insert(orderCommon);
        if(result != 1){
            log.error("生成订单异常");
            throw new ServiceException("生成订单异常");
        }

        //2.生成子订单
        log.warn("生成子订单");
        for(OrderGoods goods:orderGoodsList){
           result = orderGoodsDao.insert(goods);
           if(result != 1){
               log.error("生成子订单异常");
               throw new ServiceException("生成子订单异常");
           }
        }


        //3.删除购物车
        for(int i = 0 ; i < cartIdList.size() ; i++){
            long cartId = cartIdList.getLong(i);
            result = mallCartDao.deleteById(cartId);
            if(result != 1){
                throw new ServiceException("清除购物车异常");
            }
        }

        return orderSn;
    }

    @Override
    public String generateOfflineOrder(String memberId, String receiverName, String phoneNum, String schoolName, Long schoolId, String dormName, Long dormId, String address, JSONArray cartIdList, String province, String city, String district,BigDecimal shipFee) {
        if(shipFee == null){
            shipFee = new BigDecimal(0);
        }

        //1.生成总订单
        Date now = new Date();
        String orderSn = SnUtil.createOrderSn();

        OrderCommonOffLine orderCommon = new OrderCommonOffLine();
        orderCommon.setOrderSn(orderSn);
        orderCommon.setMemberId(memberId);
        orderCommon.setCreateTime(now);
        orderCommon.setSchoolName(schoolName);
        orderCommon.setSchoolId(schoolId);
        orderCommon.setDormName(dormName);
        orderCommon.setDormId(dormId);
        orderCommon.setAddress(address);
        orderCommon.setPhoneNum(phoneNum);
        orderCommon.setReceiverName(receiverName);
        orderCommon.setProvince(province);
        orderCommon.setCity(city);
        orderCommon.setDistrict(district);
        orderCommon.setShipFee(shipFee);
        orderCommon.setIsDelete(0);


//        List<MallGoods> mallGoodsList = new ArrayList<MallGoods>();
        BigDecimal totalCostPrice = new BigDecimal(0);

        List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
        OrderGoods orderGoods = null;
        //计算总价格
        List<MallCart> mallCartList = new ArrayList<MallCart>();
        //查询购物车所有商品总价格
        MallCart cart = null;
        for(int i = 0 ; i < cartIdList.size() ; i++){
            long cartId = cartIdList.getLong(i);
            cart = mallCartDao.findById(cartId);
            if(cart == null){
                throw new ServiceException("购物车异常");
            }
            mallCartList.add(cart);

            orderGoods = new OrderGoods();
            orderGoods.setOrderSn(orderSn);
            orderGoods.setGoodsSn(cart.getGoodsSn());
            orderGoods.setGoodsName(cart.getGoodsName());
            orderGoods.setGoodsCode(cart.getGoodsCode());
            orderGoods.setGoodsImage(cart.getGoodsImage());
            orderGoods.setNum(cart.getBuyNum());
            orderGoods.setMarketPrice(cart.getMarketPrice());
            orderGoods.setPrice(cart.getGoodsPrice());
            orderGoods.setSpecValue(cart.getSpecValue());
            orderGoods.setShipType(ShipType.SONG_HUO_SHANG_MEN.value);
            orderGoods.setShipNum(cart.getBuyNum());


            //减库存
            MallGoods mallGoods = mallGoodsDao.findByGoodsSnAndGoodsCode(cart.getGoodsSn(),cart.getGoodsCode());
            if(mallGoods != null){

                BigDecimal costPrice = mallGoods.getCostPrice();

                orderGoods.setCostPrice(costPrice);
                orderGoods.setSpecValue(mallGoods.getSpecValues());//规格值

                Integer stockNum = mallGoods.getStockNum();
                Integer buyNum = cart.getBuyNum();

                totalCostPrice = totalCostPrice.add(costPrice.multiply(new BigDecimal(buyNum)));//成本价 累加

                stockNum = stockNum - buyNum;

                if(stockNum < 0 ){
                    throw new ServiceException(mallGoods.getName() + "库存不足");
                }
//                mallGoodsList.add(mallGoods);

                mallGoods.setStockNum(stockNum);
                int result = mallGoodsDao.updateByCode(mallGoods);
                if(result != 1){
                    throw new ServiceException(mallGoods.getName() + "库存减少，更新失败");
                }
            }

            orderGoodsList.add(orderGoods);
        }

        BigDecimal totalPrice = new BigDecimal(0);//计算订单总价
        for (MallCart mallCart:mallCartList){
            Integer buyNum = mallCart.getBuyNum();
            BigDecimal goodsPrice = mallCart.getGoodsPrice();
            BigDecimal allGoodsPrice = goodsPrice.multiply(new BigDecimal(buyNum));//商品价格 * 数量
            totalPrice = totalPrice.add(allGoodsPrice);//统计总价格
        }
        orderCommon.setAmount(totalPrice);
        BigDecimal orderAmount = totalPrice.add(shipFee);
        orderCommon.setOrderAmount(orderAmount);
        orderCommon.setStatus(OrderStatus.PAYED_NO_DELIVER.value);//已支付
        orderCommon.setRefundStatus(RefundStatus.NORMAL.value);
        orderCommon.setPayType(PayType.OFFLINE.value);


        //---------------计算分润 start --------------------
        //1、配送员
        UserSchoolDormManage userSchoolDormManage = userSchoolDormManageDao.findByDormId(dormId);
        if(userSchoolDormManage != null){
            orderCommon.setDeliverUserId(userSchoolDormManage.getUserId());
            orderCommon.setDeliverShare(shipFee);
        }else{
            log.error("xxxxxxxxxxxxxxxxx订单无配送员xxxxxxxxxxxxxxxxxxx");
        }
        //2、供应商
        UserSchoolDormSupplier userSchoolDormSupplier = userSchoolDormSupplierDao.findBySchoolId(schoolId);
        if(userSchoolDormSupplier != null){
            orderCommon.setMerchantShare(totalCostPrice);
            orderCommon.setMerchantUserId(userSchoolDormSupplier.getUserId());
        }else{
            log.error("xxxxxxxxxxxxxxxxx订单无供应商xxxxxxxxxxxxxxxxxxx");
        }
        //3.楼长分润
        UserSchoolDorm userSchoolDorm = userSchoolDormDao.findByDormId(dormId);
        if(userSchoolDorm != null){
            String userId = userSchoolDorm.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);
            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
                throw new ServiceException("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
            }

            BigDecimal shareAmount = totalPrice.subtract(totalCostPrice);//可分润金额
            BigDecimal userAmount  = shareAmount.multiply(userInfo.getDistributionRatio());//代理商分润
            orderCommon.setRegionUserId(userId);
            orderCommon.setDistributionRatio(userInfo.getDistributionRatio());
            orderCommon.setRegionShare(userAmount);
        }else{
            log.error("xxxxxxxxxxxxxxxxx订单无楼长xxxxxxxxxxxxxxxxxxx");
        }

        //---------------计算分润 end --------------------



        int result = orderCommonOffLineDao.insert(orderCommon);
        if(result != 1){
            throw new ServiceException("生成订单异常");
        }

        //2.生成子订单
        for(OrderGoods goods:orderGoodsList){
            result = orderGoodsDao.insert(goods);
            if(result != 1){
                throw new ServiceException("生成子订单异常");
            }
        }

        //3.删除购物车
        for(int i = 0 ; i < cartIdList.size() ; i++){
            long cartId = cartIdList.getLong(i);
            result = mallCartDao.deleteById(cartId);
            if(result != 1){
                throw new ServiceException("清除购物车异常");
            }
        }

        return orderSn;
    }

    @Override
    public PageInfo<OrderCommon> orderListPage(PageCondition condition, Long status, String memberId) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommon> orderCommonList = orderCommonDao.findByMemberIdAndStatus(memberId,status);
        log.warn("orderCommonList:{}",orderCommonList.toString());
        for (OrderCommon orderCommon:orderCommonList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);
        }
        PageInfo<OrderCommon> orderCommonPageInfo = new PageInfo<OrderCommon>(orderCommonList);
        return orderCommonPageInfo;
    }


    @Override
    public PageInfo<OrderCommonOffLine> offlineOrderListPage(PageCondition condition, Long status, String memberId) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineDao.findByMemberIdAndStatus(memberId,status);
        log.info("orderCommonOffLineList:{}",orderCommonOffLineList.toString());
        for (OrderCommonOffLine orderCommon:orderCommonOffLineList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);

        }
        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = new PageInfo<OrderCommonOffLine>(orderCommonOffLineList);
        return orderCommonOffLinePageInfo;
    }


    @Override
    public PageInfo<OrderCommon> userOrderListPage(PageCondition condition, Long status, String userId,int type) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommon> orderCommonList = orderCommonDao.findByUserAndStatus(userId,status,type);
        for (OrderCommon orderCommon:orderCommonList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);
        }
        PageInfo<OrderCommon> orderCommonPageInfo = new PageInfo<OrderCommon>(orderCommonList);
        return orderCommonPageInfo;
    }

    @Override
    public PageInfo<OrderCommonOffLine> userOfflineOrderListPage(PageCondition condition, Long status,String userId,int type) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineDao.findByUserAndStatus(userId,status,type);
        for (OrderCommonOffLine orderCommon:orderCommonOffLineList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);
        }
        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = new PageInfo<OrderCommonOffLine>(orderCommonOffLineList);
        return orderCommonOffLinePageInfo;
    }

    @Override
    public int cancelOrderBySn(String orderSn, String memberId) {
        OrderCommon orderCommon = orderCommonDao.findByOrderSn(orderSn);
        if (orderCommon == null){
            log.error("订单不存在");
            return 0;
        }

        if(!orderCommon.getMemberId().equals(memberId)){
            log.error("订单不属于该会员");
            return 0;
        }

        orderCommon.setStatus(OrderStatus.CANCEL.value);
        int result = orderCommonDao.updateByOrderSn(orderCommon);
        return result;
    }

    @Override
    public int cancelOfflineOrderBySn(String orderSn, String memberId) {
        OrderCommonOffLine orderCommonOffLine = orderCommonOffLineDao.findByOrderSn(orderSn);
        if (orderCommonOffLine == null){
            log.error("订单不存在");
            return 0;
        }

        if(!orderCommonOffLine.getMemberId().equals(memberId)){
            log.error("订单不属于该会员");
            return 0;
        }

        orderCommonOffLine.setStatus(OrderStatus.CANCEL.value);
        int result = orderCommonOffLineDao.updateByOrderSn(orderCommonOffLine);
        return result;
    }

    @Override
    public int finishOrderBySn(String orderSn, String memberId) {
        OrderCommon orderCommon = orderCommonDao.findByOrderSn(orderSn);

        if (orderCommon == null){
            log.error("订单不存在");
            return 0;
        }

        if(!orderCommon.getMemberId().equals(memberId)){
            log.error("订单不属于该会员");
            return 0;
        }

        orderCommon.setStatus(OrderStatus.ORDER_FINISH.value);
        Date now = new Date();
        orderCommon.setFinishedTime(now);
        int result = orderCommonDao.updateByOrderSn(orderCommon);
        return result;
    }

    @Override
    public int finishOfflineOrderBySn(String orderSn, String memberId) {
        OrderCommonOffLine orderCommonOffLine = orderCommonOffLineDao.findByOrderSn(orderSn);
        if (orderCommonOffLine == null){
            log.error("订单不存在");
            return 0;
        }

        if(!orderCommonOffLine.getMemberId().equals(memberId)){
            log.error("订单不属于该会员");
            return 0;
        }

        orderCommonOffLine.setStatus(OrderStatus.ORDER_FINISH.value);
        Date now = new Date();
        orderCommonOffLine.setFinishedTime(now);
        int result = orderCommonOffLineDao.updateByOrderSn(orderCommonOffLine);
        return result;
    }

    @Override
    public int updateOrderStatusBySn(String orderSn, int value) {
        OrderCommon orderCommon = orderCommonDao.findByOrderSn(orderSn);
        orderCommon.setStatus(value);
        int result = orderCommonDao.updateByOrderSn(orderCommon);
        return result;
    }

    @Override
    public int updateOfflineOrderStatusBySn(String orderSn, int value) {
        OrderCommonOffLine orderCommonOffLine = orderCommonOffLineDao.findByOrderSn(orderSn);
        orderCommonOffLine.setStatus(value);
        int result = orderCommonOffLineDao.updateByOrderSn(orderCommonOffLine);
        return result;
    }


    @Autowired
    private AccountBillOfflineDao accountBillOfflineDao;

    @Transactional
    @Override
    public int finishOfflineOrderStatusBySn(String orderSn, int value) {
        OrderCommonOffLine orderCommonOffLine = orderCommonOffLineDao.findByOrderSn(orderSn);
        orderCommonOffLine.setStatus(value);
        int result = orderCommonOffLineDao.updateByOrderSn(orderCommonOffLine);
        if(result != 1){
            log.error("更新订单状态失败");
            return 0;
        }
        log.warn("..............开始分润...........");

        //账单分润
        //============================生成分润账单========================
        Long dormId = orderCommonOffLine.getDormId();
        Date now = new Date();
        String month = DateUtils.Long2String(now.getTime(),"yyyy-MM");

        //1、配送员分润
        BigDecimal shipFee   = orderCommonOffLine.getDeliverShare();
        String deliverUserId = orderCommonOffLine.getDeliverUserId();
        if(shipFee != null && deliverUserId != null){
            Account account = accountDao.findByUserId(deliverUserId);
            if(account != null){
                AccountBillOffline accountBill = new AccountBillOffline();
                accountBill.setAccountId(account.getAid());
                accountBill.setType(AccountBillType.ENTER.value);
                accountBill.setOrderSn(orderCommonOffLine.getOrderSn());
                accountBill.setStatus(AccountBillStatus.UNFREE.value);
                accountBill.setAmount(shipFee);//分润费用
                accountBill.setIsDelete(Boolean.FALSE);
                accountBill.setCreateTime(now);
                accountBill.setUpdateTime(now);
                accountBill.setMonth(month);
                accountBill.setBillStatus(BillStatus.SUCCESS.value);
                result = accountBillOfflineDao.addAccountBill(accountBill);
                if(result != 1){
                    throw new ServiceException("xxxxxxxxxxxxxxxxx " + deliverUserId + "线下配送员分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx配送员未开通账号xxxxxxxxxxxxxxxxxxx");
            }
        }
        log.warn("配送员分润金额:{}",shipFee);


        //2、供应商分润
        BigDecimal totalCostPrice = orderCommonOffLine.getMerchantShare();
        String merchantUserId     = orderCommonOffLine.getMerchantUserId();
        if(merchantUserId != null && totalCostPrice != null){
            Account account = accountDao.findByUserId(merchantUserId);
            if(account != null){
                AccountBillOffline accountBill = new AccountBillOffline();
                accountBill.setAccountId(account.getAid());
                accountBill.setType(AccountBillType.ENTER.value);
                accountBill.setOrderSn(orderCommonOffLine.getOrderSn());
                accountBill.setStatus(AccountBillStatus.UNFREE.value);
                accountBill.setAmount(totalCostPrice);//分润费用
                accountBill.setIsDelete(Boolean.FALSE);
                accountBill.setCreateTime(now);
                accountBill.setUpdateTime(now);
                accountBill.setMonth(month);
                accountBill.setBillStatus(BillStatus.SUCCESS.value);
                result = accountBillOfflineDao.addAccountBill(accountBill);
                if(result != 1){
                    throw new ServiceException("xxxxxxxxxxxxxxxxx " + merchantUserId + "线下供应商分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx {} 线下供应商分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",merchantUserId);
            }
        }else{
            log.error("xxxxxxxxxxxxxxxxx找不到供应商xxxxxxxxxxxxxxxxxxx");
        }
        log.warn("供应商分润金额:{}",totalCostPrice);


        //3、楼长分润
        String regionUserId   = orderCommonOffLine.getRegionUserId();
        BigDecimal userAmount = orderCommonOffLine.getRegionShare();//代理商分润
        log.warn("代理商分润比例:{}",orderCommonOffLine.getDistributionRatio());

        if(regionUserId != null && userAmount != null){
            //楼长/区长 分润账户
            Account account = accountDao.findByUserId(regionUserId);
            if(account != null){
                AccountBillOffline accountBill = new AccountBillOffline();
                accountBill.setAccountId(account.getAid());
                accountBill.setType(AccountBillType.ENTER.value);
                accountBill.setOrderSn(orderCommonOffLine.getOrderSn());
                accountBill.setStatus(AccountBillStatus.UNFREE.value);
                accountBill.setAmount(userAmount);
                accountBill.setIsDelete(Boolean.FALSE);
                accountBill.setCreateTime(now);
                accountBill.setUpdateTime(now);
                accountBill.setMonth(month);
                accountBill.setBillStatus(BillStatus.SUCCESS.value);
                result = accountBillOfflineDao.addAccountBill(accountBill);
                if(result != 1){
                    throw new ServiceException("xxxxxxxxxxxxxxxxx " + regionUserId + "线下楼长分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx {} 楼长分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",regionUserId);
            }
        }else {
            log.error("xxxxxxxxxxxxxxxxx找不到楼长xxxxxxxxxxxxxxxxxxx");
        }
        log.warn("代理商分润金额:{}",userAmount);


        //4、平台分润
        BigDecimal amount         = orderCommonOffLine.getAmount();//订单总额
        BigDecimal shareAmount    = amount.subtract(totalCostPrice);//分润金额 = 订单总额 - 运费
        BigDecimal platformAmount = shareAmount.subtract(userAmount);//分润金额 - 代理商分润
        log.warn("平台商分润金额:{}",platformAmount);
        Account platformAccount= accountDao.findByAccountId(platformAccountId);
        if(platformAccount != null){
            AccountBillOffline platformAccountBill = new AccountBillOffline();
            platformAccountBill.setAccountId(platformAccountId);
            platformAccountBill.setType(AccountBillType.ENTER.value);
            platformAccountBill.setOrderSn(orderCommonOffLine.getOrderSn());
            platformAccountBill.setStatus(AccountBillStatus.UNFREE.value);
            platformAccountBill.setAmount(platformAmount);
            platformAccountBill.setIsDelete(Boolean.FALSE);
            platformAccountBill.setCreateTime(now);
            platformAccountBill.setUpdateTime(now);
            platformAccountBill.setMonth(month);
            platformAccountBill.setBillStatus(BillStatus.SUCCESS.value);
            result = accountBillOfflineDao.addAccountBill(platformAccountBill);
            if(result != 1){
                throw new ServiceException("xxxxxxxxxxxxxxxxx " + platformAccount.getUid() + "线下冻结平台用户账单失败 xxxxxxxxxxxxxxxxxxx");
            }
        }else{
            log.error("xxxxxxxxxxxxxxxxx {} 平台用户未开通账户 xxxxxxxxxxxxxxxxxxx",platformAccount);
        }

        return 1;
    }

    @Override
    public OrderCommon findByOrderSn(String orderSn) {
        OrderCommon orderCommon = orderCommonDao.findByOrderSn(orderSn);
        return orderCommon;
    }

    @Override
    public OrderCommonOffLine findByOfflineOrderSn(String orderSn) {
        OrderCommonOffLine orderCommonOffLine = orderCommonOffLineDao.findByOrderSn(orderSn);
        return orderCommonOffLine;
    }

    @Override
    public int updateOrderBySn(OrderCommon orderCommon) {
        int result = orderCommonDao.updateByOrderSn(orderCommon);
        return result;
    }

    @Override
    public int updateOfflineOrderBySn(OrderCommonOffLine orderCommonOffLine) {
        int result = orderCommonOffLineDao.updateByOrderSn(orderCommonOffLine);
        return result;
    }

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountBillDao accountBillDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserSchoolDormDao userSchoolDormDao;

    @Autowired
    private UserSchoolDormManageDao userSchoolDormManageDao;

    @Autowired
    private UserSchoolDormSupplierDao userSchoolDormSupplierDao;


    @Value("${platform_account_id}")
    private String platformAccountId;


    /**
     * 生成分润账单
     * @param orderCommon
     * @return
     */
    @Transactional
    @Override
    public int updateOrderBySnAndShare(OrderCommon orderCommon) {
        int result = orderCommonDao.updateByOrderSn(orderCommon);
        if(result != 1){
            throw new ServiceException("订单更新失败");
        }

        //============================生成分润账单========================
        Date now = new Date();
        //1、配送员分润
        BigDecimal shipFee = orderCommon.getDeliverShare();
        String deliverUserId = orderCommon.getDeliverUserId();
        if(shipFee != null && deliverUserId != null){
            Account account = accountDao.findByUserId(deliverUserId);
            if(account != null){
                AccountBill accountBill = new AccountBill();
                accountBill.setAccountId(account.getAid());
                accountBill.setType(AccountBillType.ENTER.value);
                accountBill.setOrderSn(orderCommon.getOrderSn());
                accountBill.setStatus(AccountBillStatus.FREE.value);
                accountBill.setAmount(shipFee);//分润费用
                accountBill.setIsDelete(Boolean.FALSE);
                accountBill.setCreateTime(now);
                accountBill.setUpdateTime(now);
                accountBill.setBillStatus(BillStatus.SUCCESS.value);
                result = accountBillDao.addAccountBill(accountBill);
                if(result != 1){
                    throw new ServiceException("xxxxxxxxxxxxxxxxx " + deliverUserId + "配送员分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx配送员未开通账号xxxxxxxxxxxxxxxxxxx");
            }
        }
        log.warn("配送员分润金额:{}",shipFee);

        //2、供应商分润
        BigDecimal totalCostPrice = orderCommon.getMerchantShare();
        String merchantUserId     = orderCommon.getMerchantUserId();
        if(merchantUserId != null && totalCostPrice != null){
            Account account = accountDao.findByUserId(merchantUserId);
            if(account != null){
                AccountBill accountBill = new AccountBill();
                accountBill.setAccountId(account.getAid());
                accountBill.setType(AccountBillType.ENTER.value);
                accountBill.setOrderSn(orderCommon.getOrderSn());
                accountBill.setStatus(AccountBillStatus.FREE.value);
                accountBill.setAmount(totalCostPrice);//分润费用
                accountBill.setIsDelete(Boolean.FALSE);
                accountBill.setCreateTime(now);
                accountBill.setUpdateTime(now);
                accountBill.setBillStatus(BillStatus.SUCCESS.value);
                result = accountBillDao.addAccountBill(accountBill);
                if(result != 1){
                    throw new ServiceException("xxxxxxxxxxxxxxxxx " + merchantUserId + "供应商分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx {} 供应商分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",merchantUserId);
            }
        }
        log.warn("供应商分润金额:{}",totalCostPrice);


        //3、楼长分润
        String regionUserId   = orderCommon.getRegionUserId();
        BigDecimal userAmount = orderCommon.getRegionShare();//代理商分润
        log.warn("代理商分润比例:{}",orderCommon.getDistributionRatio());

        if(regionUserId != null && userAmount != null){
            //楼长/区长 分润账户
            Account account = accountDao.findByUserId(regionUserId);
            if(account != null){
                AccountBill accountBill = new AccountBill();
                accountBill.setAccountId(account.getAid());
                accountBill.setType(AccountBillType.ENTER.value);
                accountBill.setOrderSn(orderCommon.getOrderSn());
                accountBill.setStatus(AccountBillStatus.FREE.value);
                accountBill.setAmount(userAmount);
                accountBill.setIsDelete(Boolean.FALSE);
                accountBill.setCreateTime(now);
                accountBill.setUpdateTime(now);
                accountBill.setBillStatus(BillStatus.SUCCESS.value);
                result = accountBillDao.addAccountBill(accountBill);
                if(result != 1){
                    throw new ServiceException("xxxxxxxxxxxxxxxxx " + regionUserId+ "楼长分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx {} 楼长分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",regionUserId);
            }
        }
        log.warn("代理商分润金额:{}",userAmount);


        //4、平台分润
        BigDecimal amount         = orderCommon.getAmount();//订单总额
        BigDecimal shareAmount    = amount.subtract(totalCostPrice);//分润金额 = 订单总额 - 运费
        BigDecimal platformAmount = shareAmount.subtract(userAmount);//分润金额 - 代理商分润
        log.warn("平台商分润金额:{}",platformAmount);
        Account platformAccount= accountDao.findByAccountId(platformAccountId);
        if(platformAccount != null){
            AccountBill platformAccountBill = new AccountBill();
            platformAccountBill.setAccountId(platformAccountId);
            platformAccountBill.setType(AccountBillType.ENTER.value);
            platformAccountBill.setOrderSn(orderCommon.getOrderSn());
            platformAccountBill.setStatus(AccountBillStatus.FREE.value);
            platformAccountBill.setAmount(platformAmount);
            platformAccountBill.setIsDelete(Boolean.FALSE);
            platformAccountBill.setCreateTime(now);
            platformAccountBill.setUpdateTime(now);
            platformAccountBill.setBillStatus(BillStatus.SUCCESS.value);
            result = accountBillDao.addAccountBill(platformAccountBill);
            if(result != 1){
                throw new ServiceException("xxxxxxxxxxxxxxxxx " + platformAccount.getUid() + "冻结平台用户账单失败 xxxxxxxxxxxxxxxxxxx");
            }
        }else{
            log.error("xxxxxxxxxxxxxxxxx {} 平台用户未开通账户 xxxxxxxxxxxxxxxxxxx",platformAccount);
        }

        return 1;
    }

    @Transactional
    @Override
    public int updateOrderBySnAndShareRefund(OrderCommon orderCommon) {
        int result = orderCommonDao.updateByOrderSn(orderCommon);
        if(result != 1){
            throw new ServiceException("订单更新失败");
        }

        Date now = new Date();
        List<AccountBill> accountBillList = accountBillDao.findByOrderSn(orderCommon.getOrderSn());
        for(AccountBill accountBill:accountBillList){
            accountBill.setStatus(AccountBillStatus.CANCEL.value);
            accountBill.setUpdateTime(now);
            int reuslt = accountBillDao.updateAccountBill(accountBill);
            if(reuslt != 1){
                throw new ServiceException("账单更新失败");
            }
        }
        return 1;
    }

    @Override
    public int requestRefundOrderBySn(String orderSn, String memberId) {
        //申请退款，发公告，通知
        OrderCommon orderCommon = orderCommonDao.findByOrderSn(orderSn);
        orderCommon.setRefundStatus(RefundStatus.REFUND_REQUEST.value);
        int result = orderCommonDao.updateByOrderSn(orderCommon);
        return result;
    }

    @Override
    public int requestRefundOfflineOrderBySn(String orderSn, String memberId) {
        //申请退款，发公告，通知
        OrderCommonOffLine orderCommonOffLine = orderCommonOffLineDao.findByOrderSn(orderSn);
        orderCommonOffLine.setRefundStatus(RefundStatus.REFUND_REQUEST.value);
        int result = orderCommonOffLineDao.updateByOrderSn(orderCommonOffLine);
        return result;
    }

    @Override
    public OrderCommon findByTradeNo(String out_trade_no) {
        OrderCommon orderCommon = orderCommonDao.findByTradeNo(out_trade_no);
        return orderCommon;
    }

    @Override
    public List<OrderCommon> findTimeOutOrders(Date timeOutDate) {
        List<OrderCommon> orderCommonList  = orderCommonDao.findTimeOutNoPayOrders(timeOutDate,OrderStatus.NO_PAY.value);
        return orderCommonList;
    }

    @Override
    public List<OrderGoods> findOrderGoodsByOrderSn(String orderSn) {
        List<OrderGoods>  orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
        return orderGoodsList;
    }

    @Override
    public OrderCommon findDetailByOrderSn(String orderSn) {
        OrderCommon orderCommon = orderCommonDao.findByOrderSn(orderSn);
        if(orderCommon != null){
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);
        }
        return orderCommon;
    }

    @Override
    public OrderCommonOffLine findOfflineDetailByOrderSn(String orderSn) {
        OrderCommonOffLine orderCommonOffLine = orderCommonOffLineDao.findByOrderSn(orderSn);
        if(orderCommonOffLine != null){
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommonOffLine.setOrderGoodsList(orderGoodsList);
        }
        return orderCommonOffLine;
    }

    @Override
    public PageInfo<OrderCommon> merchantOrderListPage(PageCondition condition, Integer status, String userId) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommon> orderCommonList = orderCommonDao.findMerchantOrderList(userId,status);
        for (OrderCommon orderCommon:orderCommonList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);
        }
        PageInfo<OrderCommon> orderCommonPageInfo = new PageInfo<OrderCommon>(orderCommonList);
        return orderCommonPageInfo;
    }

    @Override
    public PageInfo<OrderCommonOffLine> merchantOrderOfflineListPage(PageCondition condition, Integer status, String userId) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineDao.findMerchantOrderList(userId,status);
        for (OrderCommonOffLine orderCommon:orderCommonOffLineList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);
        }
        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = new PageInfo<OrderCommonOffLine>(orderCommonOffLineList);
        return orderCommonOffLinePageInfo;
    }

    @Override
    public PageInfo<OrderCommonOffLine> testOfflineOrderListPage(PageCondition condition) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineDao.findOrderList();
        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = new PageInfo<OrderCommonOffLine>(orderCommonOffLineList);
        return orderCommonOffLinePageInfo;
    }
}
