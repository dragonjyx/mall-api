package com.mall.dao;

import com.mall.mapper.PaymentMapper;
import com.mall.model.Payment;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PaymentDao {

    @Autowired
    private PaymentMapper paymentMapper;

    public Payment findByCode(String code) {
        Payment payment = new Payment();
        payment.setCode(code);
        return paymentMapper.selectOne(payment);
    }

    public Payment findByName(String name) {
        Payment payment = new Payment();
        payment.setName(name);
        return paymentMapper.selectOne(payment);
    }

    public int insert(Payment payment) {
        int count = paymentMapper.insert(payment);
        return count;
    }

    public int updateById(Payment payment) {
        Example example = new Example(Payment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", payment.getId());
        int count = paymentMapper.updateByExample(payment, example);
        return count;
    }

    public Payment findById(Long id) {
        Payment payment = new Payment();
        payment.setId(id);
        return paymentMapper.selectOne(payment);
    }

    public int deleteByCode(String code) {
        Example example = new Example(Payment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        int count = paymentMapper.deleteByExample(example);
        return count;
    }

    public List<Payment> allPaymentList(){
        Example example = new Example(Payment.class);
        List<Payment> paymentList = paymentMapper.selectByExample(example);
        return paymentList;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "code", "isOnline", "status"};
        String[] legalLikeWords = { "name", "config", "info"};
        String[] legalOrderWords = { "id"};
        PageHandler<Payment> pageHandler = new PageHandler<Payment>(Payment.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, paymentMapper);
        return page;
    }
}
