package com.mall.dao;

import com.mall.mapper.StatisticsOrderStoreMapper;
import com.mall.model.StatisticsOrderStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsOrderStoreDao {

    @Autowired
    private StatisticsOrderStoreMapper sosMapper;

    public List<StatisticsOrderStore> findByDateCondition(String dateCondition) {
        Example example = new Example(StatisticsOrderStore.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", dateCondition);
        List<StatisticsOrderStore> statisticsOrderStores = sosMapper.selectByExample(example);
        return statisticsOrderStores;
    }

    public int insertBatch(List<StatisticsOrderStore> stores) {
        int num = 0;
        int count = 0;
        List<StatisticsOrderStore> insert = new ArrayList<StatisticsOrderStore>();
        for (StatisticsOrderStore StatisticsOrderAgent:stores) {
            num += 1;
            insert.add(StatisticsOrderAgent);

            if (num % 100 == 0 || num == stores.size()) {
                int result = sosMapper.insertBatch(insert);
                count += result;
                insert.clear();
            }
        }
        return count;
    }

    public int updateBatch(List<StatisticsOrderStore> stores) {
        int num = 0;
        int count = 0;
        List<StatisticsOrderStore> update = new ArrayList<StatisticsOrderStore>();
        for (StatisticsOrderStore StatisticsOrderAgent:stores) {
            num += 1;
            update.add(StatisticsOrderAgent);

            if (num % 100 == 0 || num == stores.size()) {
                int result = sosMapper.updateBatch(update);
                count += result;
                update.clear();
            }
        }
        return count;
    }
}
