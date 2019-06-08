package com.mall.dao;

import com.mall.mapper.CustomerMapper;
import com.mall.model.Customer;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class CustomerDao {

    @Autowired
    private CustomerMapper customerMapper;

    public int insert(Customer customer) {
        int count = customerMapper.insert(customer);
        return count;
    }

    public Customer findById(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer = customerMapper.selectOne(customer);
        return customer;
    }

    public int updateById(Customer cus) {
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", cus.getId());
        int count = customerMapper.updateByExample(cus, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = customerMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "phone", "status"};
        String[] legalLikeWords = { "name", "remark", "address"};
        String[] legalOrderWords = { "id", "createTime", "updateTime"};
        PageHandler<Customer> pageHandler = new PageHandler<Customer>(Customer.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, customerMapper);
        return page;
    }
}
