package com.mall.dao;


import com.mall.mapper.SettlementMapper;
import com.mall.model.Settlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;

@Service
public class SettlementDao {

    @Autowired
    private SettlementMapper settlementMapper;

    public BigDecimal sumSettlement(String userId,Integer status){
        return settlementMapper.sumSettlementByUserIdAndStatus(userId,status);
    }


}
