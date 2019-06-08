package com.mall.service.impl;

import com.mall.dao.AdvertisementDao;
import com.mall.dao.MallAddressDao;
import com.mall.dao.MallCartDao;
import com.mall.dao.MallGoodsDao;
import com.mall.model.Advertisement;
import com.mall.model.MallAddress;
import com.mall.model.MallCart;
import com.mall.model.MallGoods;
import com.mall.params.page.Page;
import com.mall.params.page.PageLiminateNull;
import com.mall.service.AdvertisementService;
import com.mall.service.MallAddressService;
import com.mall.service.MallCartService;
import com.mall.service.utils.SnUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class MallServiceImpl implements MallAddressService,MallCartService,AdvertisementService {

    @Autowired
    private MallCartDao mallCartDao;

    @Autowired
    private MallGoodsDao mallGoodsDao;


    @Override
    public int addMallCart(MallCart mallCart) {
        MallCart tmpCart = mallCartDao.findByGoodsSnAndGoodsCode(mallCart.getGoodsSn(),mallCart.getGoodsCode());
        if(tmpCart != null){
            Integer buyNum = tmpCart.getBuyNum();
            buyNum = buyNum + mallCart.getBuyNum();
            tmpCart.setBuyNum(buyNum);
            return mallCartDao.updateById(tmpCart);
        }

        MallGoods mallGoods = mallGoodsDao.findByGoodsSnAndGoodsCode(mallCart.getGoodsSn(),mallCart.getGoodsCode());
        if(mallGoods == null){
            log.error("商品已下架");
            return 0;
        }
        if(mallGoods.getStockNum() <= 0){
            log.error("商品库存不足");
            return 0;
        }

        mallCart.setGoodsPrice(mallGoods.getPrice());
        mallCart.setMarketPrice(mallGoods.getMarketPrice());
//        mallCart.setGoodsImage(mallGoods.getGoodsImage());

        int result = mallCartDao.insert(mallCart);
        return result;
    }

    @Override
    public List<MallCart> findMallCatByUid(String uid) {
        List<MallCart> mallCarts = mallCartDao.findMallCatByUid(uid);
        return mallCarts;
    }

    @Override
    public int deleteCart(long id) {
        int result = mallCartDao.deleteById(id);
        return result;
    }

    @Override
    public int deleteAllCart(String memberId) {
        int result = mallCartDao.deleteAllCart(memberId);
        return result;
    }

    @Override
    public int setCartGoodsNum(Long id, Integer number) {
        MallCart mallCart = mallCartDao.findById(id);
        if(mallCart == null){
            log.error("购物车商品不存在");
            return 0;
        }
        mallCart.setBuyNum(number);
        int result = mallCartDao.updateById(mallCart);
        return result;
    }



    @Autowired
    private MallAddressDao mallAddressDao;

    @Override
    public Integer add(MallAddress mallAddress) {
        if(mallAddress.getIsDefault() != null &&  mallAddress.getIsDefault() == 1){
            //把全部设置为非默认
            int result = mallAddressDao.setDefault(mallAddress.getUid(),null,null);
            if(result < 0){
                return 0;
            }
        }

        String addressSn = SnUtil.createAddressSn();
        mallAddress.setAddressSn(addressSn);
        Integer result = mallAddressDao.addNewAddress(mallAddress);
        return result;
    }

    @Override
    public Integer update(MallAddress mallAddress) {
        String addressSn = mallAddress.getAddressSn();
        if (StringUtils.isEmpty(addressSn)) {
            log.warn("地址编号不能为空，addressSn:{}",addressSn);
            return 0;
        }
        int result = mallAddressDao.updateMallAddress(mallAddress);

        if(mallAddress.getIsDefault() != null && mallAddress.getIsDefault() == 1){
            setDefaultAddress(mallAddress.getUid(),addressSn,mallAddress.getIsDefault());
        }


        return result;
    }

    @Override
    public Integer delete(String addressSn) {
        int result = mallAddressDao.deleteByAddressSn(addressSn);
        return result;
    }

    @Override
    public MallAddress findAddressByUidAndAddress(String uid, String addressSn) {

        if (StringUtils.isEmpty(addressSn)) {
            log.warn("地址编号不能为空");
            return null;
        }

        if (StringUtils.isEmpty(uid)) {
            log.warn("用户uid不能为空");
            return null;
        }

        MallAddress mallAddress = mallAddressDao.findAddressByAddressSn(addressSn);
        if (mallAddress == null) {
            log.warn("地址查询为空");
            return null;
        }

        if (!mallAddress.getUid().equals(uid)) {
            log.warn("用户信息不匹配");
            return null;
        }


        return mallAddress;
    }

    @Override
    public List<MallAddress> findAllAddressByUid(String uid) {
        return mallAddressDao.findAllMallAddressByUid(uid);
    }

    @Override
    public Page pageAddress(Page page) {

        page = PageLiminateNull.liminateNull(page);
        if (StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("id desc");
        }

        return mallAddressDao.pageAddress(page);
    }

    @Override
    public MallAddress findByAddressSn(String addressSn) {
        MallAddress mallAddress = mallAddressDao.findAddressByAddressSn(addressSn);
        return mallAddress;
    }

    @Override
    public MallAddress findMyDefaultByAddressSn(String memberId) {
        MallAddress mallAddress = mallAddressDao.findDefaultAddress(memberId);
        return mallAddress;
    }

    @Override
    public int setDefaultAddress(String memberId, String addressSn, Integer isDefault) {
        //把全部设置为非默认
        int result = mallAddressDao.setDefault(memberId,null,null);
        if(result < 0){
            return 0;
        }
        result = mallAddressDao.setDefault(memberId, addressSn, isDefault);
        return result;
    }


    @Autowired
    private AdvertisementDao advertisementDao;


    @Override
    public List<Advertisement> listAdvertisement() {
        List<Advertisement>  advertisementList = advertisementDao.findByType(null);
        return advertisementList;
    }
}
