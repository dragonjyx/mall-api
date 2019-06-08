package com.mall.dao;

import com.mall.mapper.WorkflowLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowLineLogsDao {

    @Autowired
    private WorkflowLineMapper workflowLineMapper;


}
