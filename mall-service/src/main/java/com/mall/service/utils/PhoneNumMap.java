package com.mall.service.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DRAGON on 2017/10/31.
 */
public class PhoneNumMap {

    private static Map<String,String> phoneNumMap = new ConcurrentHashMap<String,String>();

    public boolean addData(String phoneNum,String openId){
        if (StringUtils.isEmpty(phoneNum) || StringUtils.isEmpty(openId)){
            return false;
        }
        try {
            phoneNumMap.put(phoneNum,openId);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public String getData(String phoneNum){
        return phoneNumMap.get(phoneNum);
    }

    public void removeData(String phoneNum){
        phoneNumMap.remove(phoneNum);
    }


}
