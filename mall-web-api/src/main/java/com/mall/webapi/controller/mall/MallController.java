package com.mall.webapi.controller.mall;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.java.response.JsonResult;
import com.mall.model.Advertisement;
import com.mall.model.DistributionFee;
import com.mall.model.MallGoodsCommon;
import com.mall.model.MallType;
import com.mall.params.page.PageCondition;
import com.mall.service.AdvertisementService;
import com.mall.service.GoodsService;
import com.mall.service.MallTypeService;
import com.mall.webapi.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商城API
 */
@Slf4j
@Controller
@RequestMapping("mall")
public class MallController extends BaseController {


    @Autowired
    private MallTypeService mallTypeService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AdvertisementService advertisementService;


    /**
     * 热门商品 广告
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "hot-goods/list/query",method = RequestMethod.GET)
    public JsonResult queryHotGoods(HttpServletRequest request){
        List<Advertisement> advertisementList = advertisementService.listAdvertisement();
        return JsonResult.success(advertisementList);
    }



    /**
     * 商品分类列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goods-class/query",method = RequestMethod.GET)
    public JsonResult queryGoodsClass(HttpServletRequest request){
        List<MallType> mallTypeList = mallTypeService.findAllMallType();
        return JsonResult.success(mallTypeList);
    }



    /**
     * 首页列表,首页默认热门列表，搜索关键字：热门，分类，名字 模糊查询 dormId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goods-list/query",method = RequestMethod.POST)
    public JsonResult queryGoodsList(HttpServletRequest request, @RequestBody JSONObject params){
        String goodsName  = params.getString("goodsName");
        Long typeId       = params.getLong("typeId");
        Long schoolDormId = params.getLong("dormId");

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

        PageInfo<MallGoodsCommon> mallGoodsCommonPageInfo = goodsService.goodsListPage(condition,goodsName,typeId,schoolDormId);
        return JsonResult.success(mallGoodsCommonPageInfo);
    }



    /**
     * 商品详情页
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goods-detail/query/{sn}",method = RequestMethod.GET)
    public JsonResult goodsDetails(HttpServletRequest request, @PathVariable("sn")String sn){
        //总商品
        JSONObject goodsDetail = goodsService.findDetailByGoodsSn(sn);
        if (goodsDetail == null){
            return JsonResult.fail("查询失败");
        }
        return JsonResult.success(goodsDetail);
    }


    /**
     * 订单运费
     * @param request
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "order-ship-fee/{type}",method = RequestMethod.GET)
    public JsonResult orderShipFee(HttpServletRequest request, @PathVariable("type")Integer type){
        DistributionFee distributionFee = goodsService.findbyType(type);
        if(distributionFee == null){
            return JsonResult.fail("运费查询失败");
        }
        return JsonResult.success(distributionFee);
    }


}
