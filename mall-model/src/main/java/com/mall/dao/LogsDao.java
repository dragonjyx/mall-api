package com.mall.dao;

import com.mall.mapper.LogsMapper;
import com.mall.model.Logs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class LogsDao {

    @Autowired
    private LogsMapper logsMapper;

    public int addNewLogs(Logs logs){
        int result = logsMapper.insertSelective(logs);
        return result;
    }

    public List<Logs> listLogs(String userId, String name){
        Example example = new Example(Logs.class);
        if(!StringUtils.isEmpty(userId)) {
            String userName = "%"+name+"%";
            example.createCriteria().andLike("userName", userName);
        }
        if(!StringUtils.isEmpty(userId)) {
            example.createCriteria().andEqualTo("uid", userId);
        }
        example.setOrderByClause("commitDate desc");
        List<Logs> logsList = logsMapper.selectByExample(example);
        return logsList;
    }

}
