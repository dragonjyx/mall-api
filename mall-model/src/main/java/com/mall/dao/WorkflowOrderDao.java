package com.mall.dao;

import com.mall.mapper.WorkflowOrderMapper;
import com.mall.model.WorkflowOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowOrderDao {

    @Autowired
    private WorkflowOrderMapper workflowOrderMapper;

    public int insert(WorkflowOrder workflowOrder) {
        int count = workflowOrderMapper.insert(workflowOrder);
        return count;
    }

    public WorkflowOrder findByExampleId(String exampleId) {
        WorkflowOrder workflowOrder = new WorkflowOrder();
        workflowOrder.setExampleId(exampleId);
        workflowOrder = workflowOrderMapper.selectOne(workflowOrder);
        return workflowOrder;
    }
}
