package com.mall.mapper;

import com.mall.model.WorkflowExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WorkflowExampleMapper extends Mapper<WorkflowExample> {


    List<WorkflowExample> findByUserIdAndStatus(@Param("userId") String userId, @Param("statusList") List<Integer> statusList);



}