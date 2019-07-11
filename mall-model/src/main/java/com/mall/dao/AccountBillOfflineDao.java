package com.mall.dao;

import com.mall.mapper.AccountBillOfflineMapper;
import com.mall.model.AccountBill;
import com.mall.model.AccountBillOffline;
import com.mall.params.status.AccountBillOfflineSettleStatus;
import com.mall.params.status.AccountBillStatus;
import com.mall.params.status.AccountBillType;
import com.mall.params.status.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AccountBillOfflineDao {

    @Autowired
    private AccountBillOfflineMapper accountBillOfflineMapper;

    public int addAccountBill(AccountBillOffline accountBill) {
        int result = accountBillOfflineMapper.insertSelective(accountBill);
        return result;
    }


    public BigDecimal sumFreeAmount(String accountId){
        BigDecimal withdrawAmount = accountBillOfflineMapper.sumAmount(accountId,AccountBillType.ENTER.value,AccountBillStatus.FREE.value,BillStatus.SUCCESS.value,AccountBillOfflineSettleStatus.NO_SETTLE.value);
        return withdrawAmount;
    }

    public BigDecimal sumSettleAmount(String accountId){
        BigDecimal withdrawAmount = accountBillOfflineMapper.sumAmount(accountId,AccountBillType.ENTER.value,AccountBillStatus.UNFREE.value,BillStatus.SUCCESS.value,AccountBillOfflineSettleStatus.NO_SETTLE.value);
        return withdrawAmount;
    }

    public List<AccountBillOffline> findBillList(String accountId, Integer type, Date stateDate, Date endDate) {
        List<AccountBillOffline> accountBillOfflineList = accountBillOfflineMapper.findBillList(accountId,type,stateDate,endDate);
        return accountBillOfflineList;
    }
}
