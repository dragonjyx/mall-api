package com.mall.service.utils;

import com.java.utils.code.IDGenerate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SnUtil {

    public static String createAddressSn(){
        String today = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String uuid  = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "A" + today+uuid;
    }

    public static String createAfterSaleSn(){
        String today = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String uuid  = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "AS" + today+uuid;
    }

    public static String createOrderSn(){
        String today = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String uuid  = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "O" + today+uuid;
    }

    public static String createSendSn(){
        String today = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String uuid  = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "S" + today+uuid;
    }

    public static String createGoodsCommonSn(){
        String today = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String uuid  = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "G" + today+uuid;
    }

    public static String createProduceSn(){
        String today = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String uuid  = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "P" + today+uuid;
    }

    public static String createGoodsCode(){
        String today = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String uuid  = UUID.randomUUID().toString().replace("-", "").substring(0,6);
        return "C" + today+uuid;
    }

    public static String createMallAttributeCode(){
        return "A" + IDGenerate.getRandomLeter(2).toUpperCase() + (int)((Math.random()*9+1)*10000);
    }

    public static String createMallSpecValueCode(){
        return "S" + IDGenerate.getRandomLeter(2).toUpperCase() + (int)((Math.random()*9+1)*10000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(createApplySn());
        }
    }

    public static String createApplySn() {
        return "a" + UUID.randomUUID().toString().replace("-", "");
    }

    public static String createContractSn() {
        return "c" + UUID.randomUUID().toString().replace("-", "");
    }
}
