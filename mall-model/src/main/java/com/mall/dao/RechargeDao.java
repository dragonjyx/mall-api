package com.mall.dao;

import com.mall.mapper.RechargeMapper;
import com.mall.model.Recharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeDao {

    @Autowired
    private RechargeMapper rechargeMapper;


    public int addNewRecharge(Recharge recharge){
        int result = rechargeMapper.insertSelective(recharge);
        return result;
    }







}
