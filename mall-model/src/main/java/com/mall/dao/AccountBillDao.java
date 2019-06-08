package com.mall.dao;

import com.mall.mapper.AccountBillMapper;
import com.mall.model.AccountBill;
import com.mall.params.resp.AccountBillResp;
import com.mall.params.status.AccountBillStatus;
import com.mall.params.status.AccountBillType;
import com.mall.params.status.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AccountBillDao {

    @Autowired
    private AccountBillMapper accountBillMapper;


    public int addAccountBill(AccountBill accountBill){
        int result = accountBillMapper.insertSelective(accountBill);
        return result;
    }


    public List<AccountBillResp> findAccountBillList(String name, String phone, String email, String userId, Integer status, Integer type, Date stateDate, Date endDate){
        return accountBillMapper.findByNameOrPhoneOrEmail(name,phone,email,userId,status,type,stateDate,endDate);
    }

    public List<AccountBillResp> findAccountBillList(String userId, Integer status, Integer type, Date stateDate, Date endDate){
        return accountBillMapper.findByType(userId,status,type,stateDate,endDate);
    }

    public BigDecimal sumUnFreeAmount(){
        return null;
    }


    public List<AccountBill> findFreeAccountBill(Date offectDay, int status, int type) {
        Example example = new Example(AccountBill.class);
        example.createCriteria().andEqualTo("type",type)
                .andEqualTo("status",status)
                .andLessThan("createTime",offectDay);
        List<AccountBill> accountBillList = accountBillMapper.selectByExample(example);
        return accountBillList;
    }

    public int updateAccountBill(AccountBill accountBill) {
        Example example = new Example(AccountBill.class);
        example.createCriteria().andEqualTo("id",accountBill.getId());
        int result = accountBillMapper.updateByExample(accountBill,example);
        return result;
    }

    public List<AccountBill> findByOrderSn(String orderSn) {
        Example example = new Example(AccountBill.class);
        example.createCriteria().andEqualTo("orderSn",orderSn);
        List<AccountBill> accountBillList = accountBillMapper.selectByExample(example);
        return accountBillList;
    }


    public BigDecimal sumWithdrawAmount(String accountId){
        BigDecimal withdrawAmount = accountBillMapper.sumAmount(accountId,AccountBillType.WIDTHDRAW.value,AccountBillStatus.UNFREE.value,BillStatus.SUCCESS.value);
        return withdrawAmount;
    }

    public BigDecimal sumFreeAmount(String accountId){
        BigDecimal withdrawAmount = accountBillMapper.sumAmount(accountId,AccountBillType.ENTER.value,AccountBillStatus.FREE.value,BillStatus.SUCCESS.value);
        return withdrawAmount;
    }


    public List<AccountBill> findBillList(String accountId, Integer type, Date stateDate, Date endDate) {
        List<AccountBill> accountBillList = accountBillMapper.findBillList(accountId,type,stateDate,endDate);
        return accountBillList;
    }

}
