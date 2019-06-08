package com.mall.dao;

import com.mall.mapper.StatisticsOrderDeviceTypeMapper;
import com.mall.model.StatisticsOrderDeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsOrderDeviceTypeDao {

    @Autowired
    private StatisticsOrderDeviceTypeMapper sodtMapper;

    public List<StatisticsOrderDeviceType> findByDateCondition(String dateCondition) {
        Example example = new Example(StatisticsOrderDeviceType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", dateCondition);
        List<StatisticsOrderDeviceType> statisticsOrderDeviceTypes = sodtMapper.selectByExample(example);
        return statisticsOrderDeviceTypes;
    }

    public int insertBatch(List<StatisticsOrderDeviceType> agents) {
        int num = 0;
        int count = 0;
        List<StatisticsOrderDeviceType> insert = new ArrayList<StatisticsOrderDeviceType>();
        for (StatisticsOrderDeviceType StatisticsOrderDeviceType:agents) {
            num += 1;
            insert.add(StatisticsOrderDeviceType);

            if (num % 100 == 0 || num == agents.size()) {
                int result = sodtMapper.insertBatch(insert);
                count += result;
                insert.clear();
            }
        }
        return count;
    }

    public int updateBatch(List<StatisticsOrderDeviceType> agents) {
        int num = 0;
        int count = 0;
        List<StatisticsOrderDeviceType> update = new ArrayList<StatisticsOrderDeviceType>();
        for (StatisticsOrderDeviceType StatisticsOrderDeviceType:agents) {
            num += 1;
            update.add(StatisticsOrderDeviceType);

            if (num % 100 == 0 || num == agents.size()) {
                int result = sodtMapper.updateBatch(update);
                count += result;
                update.clear();
            }
        }
        return count;
    }
}
