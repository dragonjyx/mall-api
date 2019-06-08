package com.mall.mapper;

import com.mall.model.WorkflowLineLogs;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WorkflowLineLogsMapper extends Mapper<WorkflowLineLogs> {

    List<WorkflowLineLogs> findUserNoDealLogs(@Param("userId") String userId, @Param("statusList") List<Integer> statusList);


}
