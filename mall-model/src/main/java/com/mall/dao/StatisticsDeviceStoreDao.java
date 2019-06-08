package com.mall.dao;

import com.mall.mapper.StatisticsDeviceStoreMapper;
import com.mall.model.StatisticsDeviceStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsDeviceStoreDao {

    @Autowired
    private StatisticsDeviceStoreMapper sdsMapper;

    public List<StatisticsDeviceStore> findByDateCondition(String dateCondition) {
        Example example = new Example(StatisticsDeviceStore.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", dateCondition);
        List<StatisticsDeviceStore> statisticsDeviceStores = sdsMapper.selectByExample(example);
        return statisticsDeviceStores;
    }

    public int insertBatch(List<StatisticsDeviceStore> stores) {
        int num = 0;
        int count = 0;
        List<StatisticsDeviceStore> insert = new ArrayList<StatisticsDeviceStore>();
        for (StatisticsDeviceStore StatisticsDeviceAgent:stores) {
            num += 1;
            insert.add(StatisticsDeviceAgent);

            if (num % 100 == 0 || num == stores.size()) {
                int result = sdsMapper.insertBatch(insert);
                count += result;
                insert.clear();
            }
        }
        return count;
    }

    public int updateBatch(List<StatisticsDeviceStore> stores) {
        int num = 0;
        int count = 0;
        List<StatisticsDeviceStore> update = new ArrayList<StatisticsDeviceStore>();
        for (StatisticsDeviceStore StatisticsDeviceAgent:stores) {
            num += 1;
            update.add(StatisticsDeviceAgent);

            if (num % 100 == 0 || num == stores.size()) {
                int result = sdsMapper.updateBatch(update);
                count += result;
                update.clear();
            }
        }
        return count;
    }
}
