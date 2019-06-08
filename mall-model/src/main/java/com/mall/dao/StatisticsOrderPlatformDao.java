package com.mall.dao;

import com.mall.mapper.StatisticsOrderPlatformMapper;
import com.mall.model.StatisticsOrderPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class StatisticsOrderPlatformDao {

    @Autowired
    private StatisticsOrderPlatformMapper sofMapper;

    public List<StatisticsOrderPlatform> selectByDateCondition(String dateCondition) {

        Example example = new Example(StatisticsOrderPlatform.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", dateCondition);
        List<StatisticsOrderPlatform> statisticsOrderPlatforms = sofMapper.selectByExample(example);
        return statisticsOrderPlatforms;
    }

    public int insert(StatisticsOrderPlatform statisticsOrderPlatform) {
        int count = sofMapper.insert(statisticsOrderPlatform);
        return count;
    }

    public int updateByDateConditionAndOrderType(StatisticsOrderPlatform statisticsOrderPlatform) {
        Example example = new Example(StatisticsOrderPlatform.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", statisticsOrderPlatform.getDateCondition());
        criteria.andEqualTo("orderType", statisticsOrderPlatform.getOrderType());
        int count = sofMapper.updateByExample(statisticsOrderPlatform, example);
        return count;
    }
}
