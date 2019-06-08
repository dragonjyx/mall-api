package com.mall.dao;

import com.mall.mapper.StatisticsDeviceAgentMapper;
import com.mall.model.StatisticsDeviceAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsDeviceAgentDao {

    @Autowired
    private StatisticsDeviceAgentMapper soaMapper;

    public List<StatisticsDeviceAgent> findByDateCondition(String dateCondition) {
        Example example = new Example(StatisticsDeviceAgent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", dateCondition);
        List<StatisticsDeviceAgent> statisticsDeviceAgents = soaMapper.selectByExample(example);
        return statisticsDeviceAgents;
    }

    public int insertBatch(List<StatisticsDeviceAgent> agents) {
        int num = 0;
        int count = 0;
        List<StatisticsDeviceAgent> insert = new ArrayList<StatisticsDeviceAgent>();
        for (StatisticsDeviceAgent StatisticsDeviceAgent:agents) {
            num += 1;
            insert.add(StatisticsDeviceAgent);

            if (num % 100 == 0 || num == agents.size()) {
                int result = soaMapper.insertBatch(insert);
                count += result;
                insert.clear();
            }
        }
        return count;
    }

    public int updateBatch(List<StatisticsDeviceAgent> agents) {
        int num = 0;
        int count = 0;
        List<StatisticsDeviceAgent> update = new ArrayList<StatisticsDeviceAgent>();
        for (StatisticsDeviceAgent StatisticsDeviceAgent:agents) {
            num += 1;
            update.add(StatisticsDeviceAgent);

            if (num % 100 == 0 || num == agents.size()) {
                int result = soaMapper.updateBatch(update);
                count += result;
                update.clear();
            }
        }
        return count;
    }
}
