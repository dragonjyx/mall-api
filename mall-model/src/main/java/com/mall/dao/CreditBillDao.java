package com.mall.dao;

import com.mall.mapper.CreditBillMapper;
import com.mall.model.CreditBill;
import com.mall.params.resp.CreditBillResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CreditBillDao {

    @Autowired
    private CreditBillMapper creditBillMapper;


    public int addBill(CreditBill creditBill){
        int result = creditBillMapper.insertSelective(creditBill);
        return result;
    }



    public List<CreditBillResp> findAccountBillList(String name, String phone, String email, String userId, Integer type, Date stateDate, Date endDate){
        return creditBillMapper.findByNameOrPhoneOrEmail(name,phone,email,userId,type,stateDate,endDate);
    }

    public List<CreditBillResp> findAccountBillList( String userId, Integer type, Date stateDate,  Date endDate){
        return creditBillMapper.findByType(userId,type,stateDate,endDate);
    }




}
