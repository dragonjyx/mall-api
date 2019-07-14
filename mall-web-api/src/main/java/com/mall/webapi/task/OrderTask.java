package com.mall.webapi.task;

import com.java.utils.date.DateUtils;
import com.java.validate.ServiceException;
import com.mall.dao.MallGoodsDao;
import com.mall.model.MallGoods;
import com.mall.model.OrderCommon;
import com.mall.model.OrderGoods;
import com.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 订单任务
 * 清除未支付的订单
 */
@Slf4j
public class OrderTask extends QuartzJobBean {

    @Value("${order_timeout_seconds}")
    private long order_timeout_seconds;//默认7200秒

    @Autowired
    private OrderService orderService;

    @Autowired
    private MallGoodsDao mallGoodsDao;




    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.warn("------------OrderTask time:{}-------------",DateUtils.Long2String(System.currentTimeMillis(),DateUtils.YYYY_MM_dd_HH_mm_ss));
        //1.已经生成的订单，未支付，超过两个小时
        Date now = new Date();
        long timeOut = now.getTime() - order_timeout_seconds;

        Date timeOutDate = new Date(timeOut);
        List<OrderCommon> orderCommonList = orderService.findTimeOutOrders(timeOutDate);

        //2.库存调整
        for(OrderCommon orderCommon:orderCommonList){
            String orderSn = orderCommon.getOrderSn();
            List<OrderGoods> orderGoodsList = orderService.findOrderGoodsByOrderSn(orderSn);

            for(OrderGoods orderGoods:orderGoodsList){
                //3.加库存
                MallGoods mallGoods = mallGoodsDao.findByGoodsSnAndGoodsCode(orderGoods.getGoodsSn(),orderGoods.getGoodsCode());
                if(mallGoods != null){

                    Integer stockNum = mallGoods.getStockNum();
                    Integer buyNum = orderGoods.getNum();

                    stockNum = stockNum + buyNum;

                    if(stockNum < 0 ){
                        throw new ServiceException(mallGoods.getName() + "库存异常");
                    }

                    mallGoods.setStockNum(stockNum);
                    int result = mallGoodsDao.updateByCode(mallGoods);
                    if(result != 1){
                        throw new ServiceException(mallGoods.getName() + "库存添加，更新失败");
                    }
                }
            }

            //把订单置为失效
           int result  = orderService.setExpireOrder(orderSn);
            if(result != 1){
                throw new ServiceException(orderSn + "更新订单为取消状态失败");
            }

        }







    }


}
