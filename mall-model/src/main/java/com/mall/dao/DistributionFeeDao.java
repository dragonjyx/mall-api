package com.mall.dao;

import com.mall.mapper.DistributionFeeMapper;
import com.mall.model.DistributionFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DistributionFeeDao {

    @Autowired
    private DistributionFeeMapper distributionFeeMapper;


    public DistributionFee findBySchoolType(Integer type){
        Example example = new Example(DistributionFee.class);
        example.createCriteria().andEqualTo("schoolType",type);
        List<DistributionFee> distributionFeeList = distributionFeeMapper.selectByExample(example);
        if(distributionFeeList.isEmpty()){
            return null;
        }
        return distributionFeeList.get(0);
    }




}
