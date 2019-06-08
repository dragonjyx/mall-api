package com.mall.dao;

import com.mall.mapper.StatisticsOrderAgentMapper;
import com.mall.mapper.UserMapper;
import com.mall.model.StatisticsOrderAgent;
import com.mall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsOrderAgentDao {

    @Autowired
    private StatisticsOrderAgentMapper soaMapper;

    @Autowired
    private UserMapper userMapper;

    public List<StatisticsOrderAgent> findByDateCondition(String dateCondition) {
        Example example = new Example(StatisticsOrderAgent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dateCondition", dateCondition);
        List<StatisticsOrderAgent> statisticsOrderAgents = soaMapper.selectByExample(example);
        return statisticsOrderAgents;
    }

    public int insertBatch(List<StatisticsOrderAgent> agents) {
        int num = 0;
        int count = 0;
        List<Object> uids = new ArrayList<>();
        List<StatisticsOrderAgent> insert = new ArrayList<StatisticsOrderAgent>();
        for (StatisticsOrderAgent statisticsOrderAgent:agents) {
            num += 1;
            insert.add(statisticsOrderAgent);
            uids.add(statisticsOrderAgent.getAgentUid());
            if (num % 100 == 0 || num == agents.size()) {
                Example example = new Example(User.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andIn("uid", uids);
                List<User> users = userMapper.selectByExample(example);
                for (User user : users) {
                    for (StatisticsOrderAgent orderAgent : insert) {
                        if (orderAgent.getAgentUid().equals(user.getUid())) {
                            orderAgent.setAgentName(user.getUsername());
                        }
                    }
                }
                int result = soaMapper.insertBatch(insert);
                count += result;
                insert.clear();
                uids.clear();
            }
        }
        return count;
    }

    public int updateBatch(List<StatisticsOrderAgent> agents) {
        int num = 0;
        int count = 0;
        List<StatisticsOrderAgent> update = new ArrayList<StatisticsOrderAgent>();
        for (StatisticsOrderAgent StatisticsOrderAgent:agents) {
            num += 1;
            update.add(StatisticsOrderAgent);

            if (num % 100 == 0 || num == agents.size()) {
                int result = soaMapper.updateBatch(update);
                count += result;
                update.clear();
            }
        }
        return count;
    }
}
