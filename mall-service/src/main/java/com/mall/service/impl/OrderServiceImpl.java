package com.mall.service.impl;

import com.alibaba.fastjson.JSONArray;
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
        orderCommon.setShipFee(shipFee);
        orderCommon.setIsDelete(0);


//        List<MallGoods> mallGoodsList = new ArrayList<MallGoods>();

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
                orderGoods.setCostPrice(mallGoods.getCostPrice());
                orderGoods.setSpecValue(mallGoods.getSpecValues());//规格值

                Integer stockNum = mallGoods.getStockNum();
                Integer buyNum   = cart.getBuyNum();

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
            for(int i = 0; i < buyNum ; i++){
                totalPrice = totalPrice.add(goodsPrice);
            }
        }
        log.warn("计算订单总价:{}",totalPrice);
        orderCommon.setAmount(totalPrice);
        BigDecimal orderAmount = totalPrice.add(shipFee);
        orderCommon.setOrderAmount(orderAmount);
        orderCommon.setStatus(OrderStatus.NO_PAY.value);//待支付
        orderCommon.setRefundStatus(RefundStatus.NORMAL.value);
        orderCommon.setPayType(PayType.WECHAT.value);

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
                orderGoods.setCostPrice(mallGoods.getCostPrice());
                orderGoods.setSpecValue(mallGoods.getSpecValues());//规格值

                Integer stockNum = mallGoods.getStockNum();
                Integer buyNum = cart.getBuyNum();

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
            for(int i = 0; i < buyNum ; i++){
                totalPrice = totalPrice.add(goodsPrice);
            }
        }
        orderCommon.setAmount(totalPrice);
        BigDecimal orderAmount = totalPrice.add(shipFee);
        orderCommon.setOrderAmount(orderAmount);
        orderCommon.setStatus(OrderStatus.PAYED_NO_DELIVER.value);//已支付
        orderCommon.setRefundStatus(RefundStatus.NORMAL.value);
        orderCommon.setPayType(PayType.OFFLINE.value);

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
        log.warn("orderCommonOffLineList:{}",orderCommonOffLineList.toString());
        for (OrderCommonOffLine orderCommonOffLine:orderCommonOffLineList){
            String orderSn = orderCommonOffLine.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommonOffLine.setOrderGoodsList(orderGoodsList);
        }
        PageInfo<OrderCommonOffLine> orderCommonOffLinePageInfo = new PageInfo<OrderCommonOffLine>(orderCommonOffLineList);
        return orderCommonOffLinePageInfo;
    }


    @Override
    public PageInfo<OrderCommon> userOrderListPage(PageCondition condition, Long status, List<Object> dormIdList) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommon> orderCommonList = orderCommonDao.findByUserAndStatus( dormIdList,status);
        for (OrderCommon orderCommon:orderCommonList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommon.setOrderGoodsList(orderGoodsList);
        }
        PageInfo<OrderCommon> orderCommonPageInfo = new PageInfo<OrderCommon>(orderCommonList);
        return orderCommonPageInfo;
    }

    @Override
    public PageInfo<OrderCommonOffLine> userOfflineOrderListPage(PageCondition condition, Long status, List<Object> dormIdList) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderCommonOffLine> orderCommonOffLineList = orderCommonOffLineDao.findByUserAndStatus( dormIdList,status);
        for (OrderCommonOffLine orderCommonOffLine:orderCommonOffLineList){
            String orderSn = orderCommonOffLine.getOrderSn();
            List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderSn);
            orderCommonOffLine.setOrderGoodsList(orderGoodsList);
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
        Long schoolId = orderCommonOffLine.getSchoolId();
        Date now = new Date();
        String month = DateUtils.Long2String(now.getTime(),"yyyy-MM");

        //1、配送员分润
        BigDecimal shipFee = orderCommonOffLine.getShipFee();
        if(shipFee != null){
            UserSchoolDormManage userSchoolDormManage = userSchoolDormManageDao.findByDormId(dormId);
            if(userSchoolDormManage != null){
                String userId0 = userSchoolDormManage.getUserId();
                Account account = accountDao.findByUserId(userId0);
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
                        throw new ServiceException("xxxxxxxxxxxxxxxxx " + userId0+ "线下配送员分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                    }
                }else{
                    log.error("xxxxxxxxxxxxxxxxx {} 线下配送员分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",userId0);
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx找不到配送员xxxxxxxxxxxxxxxxxxx");
            }
        }

        //2、供应商分润
        BigDecimal totalCostPrice = new BigDecimal(0);
        List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderCommonOffLine.getOrderSn());
        if(!orderGoodsList.isEmpty()){
            for(OrderGoods orderGoods:orderGoodsList){
                String goodsCode = orderGoods.getGoodsCode();
                String goodsSn = orderGoods.getGoodsSn();

                Integer num = orderGoods.getNum();//购买数量
                MallGoods mallGoods = mallGoodsDao.findByGoodsSnAndGoodsCode(goodsSn,goodsCode);
                if(mallGoods != null){
                    BigDecimal costPrice = mallGoods.getCostPrice();
                    totalCostPrice = totalCostPrice.add(costPrice.multiply(new BigDecimal(num)));
                }
            }
        }
        if(totalCostPrice.compareTo(new BigDecimal(0)) == 1){
            UserSchoolDormSupplier userSchoolDormSupplier = userSchoolDormSupplierDao.findBySchoolId(schoolId);
            if(userSchoolDormSupplier != null){
                String userId0 = userSchoolDormSupplier.getUserId();
                Account account = accountDao.findByUserId(userId0);
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
                        throw new ServiceException("xxxxxxxxxxxxxxxxx " + userId0+ "线下供应商分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                    }
                }else{
                    log.error("xxxxxxxxxxxxxxxxx {} 线下供应商分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",userId0);
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx找不到供应商xxxxxxxxxxxxxxxxxxx");
            }
        }



        //3、楼长分润
        BigDecimal platformAmount = null;
        UserSchoolDorm userSchoolDorm = userSchoolDormDao.findByDormId(dormId);
        if(userSchoolDorm != null){

            String userId = userSchoolDorm.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);

            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
                throw new ServiceException("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
            }

            BigDecimal amount = orderCommonOffLine.getAmount();//订单总额

            BigDecimal bigDecimal = userInfo.getDistributionRatio();
            if(bigDecimal != null){
                BigDecimal userAmount = amount.multiply(bigDecimal);//代理商分润
                platformAmount = amount.subtract(userAmount);//平台
                //楼长/区长 分润账户
                Account account = accountDao.findByUserId(userId);
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
                        throw new ServiceException("xxxxxxxxxxxxxxxxx " + userInfo.getUid()+ "线下楼长分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                    }
                }else{
                    log.error("xxxxxxxxxxxxxxxxx {} 楼长分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",userInfo.getUid());
                }
            }else{
                platformAmount = amount;
            }
        }else{
            log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员xxxxxxxxxxxxxxxxxxx");
        }


        //4、平台分润
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
            log.error("xxxxxxxxxxxxxxxxx {} 平台用户未开通账户 xxxxxxxxxxxxxxxxxxx",platformAccount.getUid());
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
        Long dormId = orderCommon.getDormId();
        Long schoolId = orderCommon.getSchoolId();
        Date now = new Date();

        //1、配送员分润
        BigDecimal shipFee = orderCommon.getShipFee();
        if(shipFee != null){
            UserSchoolDormManage userSchoolDormManage = userSchoolDormManageDao.findByDormId(dormId);
            if(userSchoolDormManage != null){
                String userId0 = userSchoolDormManage.getUserId();
                Account account = accountDao.findByUserId(userId0);
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
                        throw new ServiceException("xxxxxxxxxxxxxxxxx " + userId0+ "配送员分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                    }
                }else{
                    log.error("xxxxxxxxxxxxxxxxx {} 配送员分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",userId0);
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx找不到配送员xxxxxxxxxxxxxxxxxxx");
            }
        }

        //2、供应商分润
        BigDecimal totalCostPrice = new BigDecimal(0);
        List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderSn(orderCommon.getOrderSn());
        if(!orderGoodsList.isEmpty()){
            for(OrderGoods orderGoods:orderGoodsList){
                String goodsCode = orderGoods.getGoodsCode();
                String goodsSn = orderGoods.getGoodsSn();

                Integer num = orderGoods.getNum();//购买数量
                MallGoods mallGoods = mallGoodsDao.findByGoodsSnAndGoodsCode(goodsSn,goodsCode);
                if(mallGoods != null){
                    BigDecimal costPrice = mallGoods.getCostPrice();
                    totalCostPrice = totalCostPrice.add(costPrice.multiply(new BigDecimal(num)));
                }
            }
        }
        if(totalCostPrice.compareTo(new BigDecimal(0)) == 1){
            UserSchoolDormSupplier userSchoolDormSupplier = userSchoolDormSupplierDao.findBySchoolId(schoolId);
            if(userSchoolDormSupplier != null){
                String userId0 = userSchoolDormSupplier.getUserId();
                Account account = accountDao.findByUserId(userId0);
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
                        throw new ServiceException("xxxxxxxxxxxxxxxxx " + userId0+ "供应商分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                    }
                }else{
                    log.error("xxxxxxxxxxxxxxxxx {} 供应商分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",userId0);
                }
            }else{
                log.error("xxxxxxxxxxxxxxxxx找不到供应商xxxxxxxxxxxxxxxxxxx");
            }
        }



        //3、楼长分润
        BigDecimal platformAmount = null;
        UserSchoolDorm userSchoolDorm = userSchoolDormDao.findByDormId(dormId);
        if(userSchoolDorm != null){

            String userId = userSchoolDorm.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);

            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
                throw new ServiceException("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
            }

            BigDecimal amount = orderCommon.getAmount();//订单总额

            BigDecimal bigDecimal = userInfo.getDistributionRatio();
            if(bigDecimal != null){
                BigDecimal userAmount = amount.multiply(bigDecimal);//代理商分润
                platformAmount = amount.subtract(userAmount);//平台
                //楼长/区长 分润账户
                Account account = accountDao.findByUserId(userId);
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
                        throw new ServiceException("xxxxxxxxxxxxxxxxx " + userInfo.getUid()+ "楼长分润,冻结用户账单失败 xxxxxxxxxxxxxxxxxxx");
                    }
                }else{
                    log.error("xxxxxxxxxxxxxxxxx {} 楼长分润,用户未开通账户 xxxxxxxxxxxxxxxxxxx",userInfo.getUid());
                }
            }else{
                platformAmount = amount;
            }
        }else{
            log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员xxxxxxxxxxxxxxxxxxx");
        }


        //4、平台分润
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
            log.error("xxxxxxxxxxxxxxxxx {} 平台用户未开通账户 xxxxxxxxxxxxxxxxxxx",platformAccount.getUid());
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
            accountBill.setBillStatus(AccountBillStatus.CANCEL.value);
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
}
