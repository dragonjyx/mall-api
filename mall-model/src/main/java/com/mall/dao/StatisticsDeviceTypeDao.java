package com.mall.dao;

import com.mall.mapper.StatisticsDeviceTypeMapper;
import com.mall.model.StatisticsDeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsDeviceTypeDao {

    @Autowired
    private StatisticsDeviceTypeMapper sdtMapper;

    public List<StatisticsDeviceType> findByDateCondition(String dateCondition) {
        Example example = new Example(StatisticsDeviceType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", dateCondition);
        List<StatisticsDeviceType> statisticsDeviceTypes = sdtMapper.selectByExample(example);
        return statisticsDeviceTypes;
    }

    public int insertBatch(List<StatisticsDeviceType> agents) {
        int num = 0;
        int count = 0;
        List<StatisticsDeviceType> insert = new ArrayList<StatisticsDeviceType>();
        for (StatisticsDeviceType StatisticsDeviceType:agents) {
            num += 1;
            insert.add(StatisticsDeviceType);

            if (num % 100 == 0 || num == agents.size()) {
                int result = sdtMapper.insertBatch(insert);
                count += result;
                insert.clear();
            }
        }
        return count;
    }

    public int updateBatch(List<StatisticsDeviceType> agents) {
        int num = 0;
        int count = 0;
        List<StatisticsDeviceType> update = new ArrayList<StatisticsDeviceType>();
        for (StatisticsDeviceType StatisticsDeviceType:agents) {
            num += 1;
            update.add(StatisticsDeviceType);

            if (num % 100 == 0 || num == agents.size()) {
                int result = sdtMapper.updateBatch(update);
                count += result;
                update.clear();
            }
        }
        return count;
    }
}
