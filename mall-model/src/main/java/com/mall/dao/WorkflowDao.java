package com.mall.dao;

import com.mall.mapper.*;
import com.mall.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class WorkflowDao {

    @Autowired
    private WorkflowMapper workflowMapper;

    @Autowired
    private WorkflowNodeMapper workflowNodeMapper;

    @Autowired
    private WorkflowLineMapper workflowLineMapper;

    @Autowired
    private WorkflowProcessMapper workflowProcessMapper;

    @Autowired
    private WorkflowExampleMapper workflowExampleMapper;

    @Autowired
    private WorkflowLineLogsMapper workflowLineLogsMapper;

    public int addWorkflow(Workflow workflow){
        int result = workflowMapper.insertSelective(workflow);
        return result;
    }

    public int updateWorkflow(Workflow workflow){
        int result = workflowMapper.updateByPrimaryKey(workflow);
        return result;
    }

    public int updateSelectiveWorkflow(Workflow workflow){
        Example example = new Example(Workflow.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", workflow.getId());
        int result = workflowMapper.updateByExampleSelective(workflow, example);
        return result;
    }

    public Workflow findWorkflowByWfNo(String wfNo){
        Example example = new Example(Workflow.class);
        example.createCriteria().andEqualTo("wfNo",wfNo);
        List<Workflow> workflowList = workflowMapper.selectByExample(example);
        if(workflowList.isEmpty()){
            return null;
        }
        return workflowList.get(0);
    }


    public int delWorkflow(int id){
        Example example = new Example(Workflow.class);
        example.createCriteria().andEqualTo("id",id);
        int result = workflowMapper.deleteByExample(example);
        return result;
    }

    public int addWorkflowNode(WorkflowNode workflowNode){
        int result = workflowNodeMapper.insertSelective(workflowNode);
        return result;
    }

    public int updateWorkflowNode(WorkflowNode workflowNode){
        int result = workflowNodeMapper.updateByPrimaryKeySelective(workflowNode);
        return result;
    }

    public List<WorkflowNode> findWorkflowNodeByName(String name){
        Example example = new Example(WorkflowNode.class);
        if(!StringUtils.isEmpty(name)){
            name = "%" + name + "%";
            example.createCriteria().andLike("name",name);
        }
        example.setOrderByClause("updateTime DESC");
        List<WorkflowNode> workflowNodeList = workflowNodeMapper.selectByExample(example);
        return workflowNodeList;
    }

    public List<WorkflowExample> findWorkflowExampleByName(String name, String userName, Integer status){
        Example example = new Example(WorkflowExample.class);
        Example.Criteria criteria= example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            name = "%" + name + "%";
            criteria.andLike("name",name);
        }
        if(!StringUtils.isEmpty(userName)){
            userName = "%" + userName + "%";
            criteria.andLike("userName",userName);
        }

        if(status != null){
            criteria.andEqualTo("status",status);
        }

        example.setOrderByClause("updateTime DESC");
        List<WorkflowExample> workflowExampleList = workflowExampleMapper.selectByExample(example);
        return workflowExampleList;
    }

    public List<WorkflowExample> findWorkflowExampleByUserId(String userId, List<Integer> statusList){
        List<WorkflowExample> workflowExampleList = workflowExampleMapper.findByUserIdAndStatus(userId,statusList);
        return workflowExampleList;
    }

    public List<WorkflowLineLogs> findWorkflowLineLogsByUserId(String userId, List<Integer> statusList){
        List<WorkflowLineLogs>  workflowLineLogsList = workflowLineLogsMapper.findUserNoDealLogs(userId,statusList);
        return workflowLineLogsList;
    }

    public List<Workflow> findWorkflowByName(String name){
        Example example = new Example(Workflow.class);
        if(!StringUtils.isEmpty(name)){
            name = "%" + name + "%";
            example.createCriteria().andLike("name",name);
        }
        example.setOrderByClause("id DESC");
        List<Workflow> workflowList = workflowMapper.selectByExample(example);
        return workflowList;
    }

    public List<WorkflowProcess> findByHandleUserNameOrExampleName(String handleUserName, String exampleName){
        Example example = new Example(WorkflowProcess.class);
        if(!StringUtils.isEmpty(handleUserName)){
            handleUserName = "%" + handleUserName + "%";
            example.createCriteria().andLike("handleUserName",handleUserName);
        }
        if(!StringUtils.isEmpty(exampleName)){
            exampleName = "%" + exampleName + "%";
            example.createCriteria().andLike("exampleName",exampleName);
        }
        example.setOrderByClause("updateTime DESC");
        List<WorkflowProcess> workflowProcessList = workflowProcessMapper.selectByExample(example);
        return workflowProcessList;
    }


    public int delWorkflowNode(int id){
        Example example = new Example(WorkflowNode.class);
        example.createCriteria().andEqualTo("id",id);
        int result = workflowNodeMapper.deleteByExample(example);
        return result;
    }


    public WorkflowNode findWorkflowNodeById(long id){
        Example example = new Example(WorkflowNode.class);
        example.createCriteria().andEqualTo("id",id);
        List<WorkflowNode> workflowNodeList = workflowNodeMapper.selectByExample(example);
        if(workflowNodeList.isEmpty()){
            return null;
        }
        return workflowNodeList.get(0);
    }

    public List<WorkflowExample> findByWorkflowIdAndStatus(int wfId,Integer status){
        Example example = new Example(WorkflowExample.class);
        example.createCriteria().andEqualTo("wfId",wfId);
        List<WorkflowExample> workflowExampleList = workflowExampleMapper.selectByExample(example);
        return workflowExampleList;
    }

    public WorkflowExample findWorkflowExampleByExampleId(String exampleId){
        Example example = new Example(WorkflowExample.class);
        example.createCriteria().andEqualTo("exampleId",exampleId);
        List<WorkflowExample> workflowExampleList = workflowExampleMapper.selectByExample(example);
        if(workflowExampleList.isEmpty()){
            return null;
        }
        return workflowExampleList.get(0);
    }


    public int addWorkflowLine(WorkflowLine workflowLine){
        int result = workflowLineMapper.insertSelective(workflowLine);
        return result;
    }


    public WorkflowLine findByWfIdAndPreNodeId(Long wfId,Long preNodeId){
        Example example = new Example(WorkflowLine.class);
        example.createCriteria().andEqualTo("wfId",wfId).andEqualTo("preNodeId",preNodeId);
        List<WorkflowLine> workflowLineList = workflowLineMapper.selectByExample(example);
        if(workflowLineList.isEmpty()){
            return null;
        }
        return workflowLineList.get(0);
    }

    public WorkflowLine findByWfIdAndNextNodeId(Long wfId,Long nextNodeId){
        Example example = new Example(WorkflowLine.class);
        example.createCriteria().andEqualTo("wfId",wfId).andEqualTo("nextNodeId",nextNodeId);
        List<WorkflowLine> workflowLineList = workflowLineMapper.selectByExample(example);
        if(workflowLineList.isEmpty()){
            return null;
        }
        return workflowLineList.get(0);
    }



    public int delWorkflowLine(int id){
        Example example = new Example(WorkflowLine.class);
        example.createCriteria().andEqualTo("id",id);
        int result = workflowLineMapper.deleteByExample(example);
        return result;
    }


    public int delWorkflowLineByWfId(int wfId){
        Example example = new Example(WorkflowLine.class);
        example.createCriteria().andEqualTo("wfId",wfId);
        int result = workflowLineMapper.deleteByExample(example);
        return result;
    }


    public List<WorkflowLine>  findWorkflowLineByNodeId(int nodeId){
        Example example = new Example(WorkflowLine.class);
        example.createCriteria().andEqualTo("nodeId",nodeId);
        List<WorkflowLine> workflowLineList = workflowLineMapper.selectByExample(example);
        return workflowLineList;
    }


    public int addWorkflowProcess(WorkflowProcess workflowProcess){
        int result = workflowProcessMapper.insertSelective(workflowProcess);
        return result;
    }

    public int delWorkflowProcess(long id){
        Example example = new Example(WorkflowNode.class);
        example.createCriteria().andEqualTo("id",id);
        int result = workflowProcessMapper.deleteByExample(example);
        return result;
    }

    public int addWorkflowExample(WorkflowExample workflowExample){
        int result = workflowExampleMapper.insertSelective(workflowExample);
        return result;
    }

    public int delWorkflowExample(long id){
        Example example = new Example(WorkflowNode.class);
        example.createCriteria().andEqualTo("id",id);
        int result = workflowExampleMapper.deleteByExample(example);
        return result;
    }

    public WorkflowExample findWorkflowExampleById(Long exampleId){
        Example example = new Example(WorkflowExample.class);
        example.createCriteria().andEqualTo("id",exampleId);
        List<WorkflowExample> workflowExampleList = workflowExampleMapper.selectByExample(example);
        if(workflowExampleList.isEmpty()){
            return null;
        }
        return workflowExampleList.get(0);
    }

    public WorkflowExample findWorkflowExampleByExampleId(Long exampleId){
        Example example = new Example(WorkflowExample.class);
        example.createCriteria().andEqualTo("exampleId",exampleId);
        List<WorkflowExample> workflowExampleList = workflowExampleMapper.selectByExample(example);
        if(workflowExampleList.isEmpty()){
            return null;
        }
        return workflowExampleList.get(0);
    }

    public int updateWorkflowExample(WorkflowExample workflowExample){
        if(workflowExample.getId() == null){
            return 0;
        }
        int result = workflowExampleMapper.updateByPrimaryKeySelective(workflowExample);
        return result;
    }


    public List<WorkflowLine> findWorkflowLine(long wfId){
        Example example = new Example(WorkflowLine.class);
        example.createCriteria().andEqualTo("wfId",wfId);
        List<WorkflowLine> workflowLineList = workflowLineMapper.selectByExample(example);
        return workflowLineList;
    }

    public List<WorkflowLineLogs> findWorkflowLineLogsByExampleId(long exampleId){
        Example example = new Example(WorkflowLineLogs.class);
        example.createCriteria().andEqualTo("exampleId",exampleId);
        List<WorkflowLineLogs> workflowLineLogsList = workflowLineLogsMapper.selectByExample(example);
        return workflowLineLogsList;
    }

    public WorkflowLineLogs findWorkflowLineLogsByExampleIdAndWfIdAndNodeId(long exampleId,long wfid,long nodeId){
        Example example = new Example(WorkflowLineLogs.class);
        example.createCriteria().andEqualTo("exampleId",exampleId)
                .andEqualTo("wfId",wfid)
                .andEqualTo("nodeId",nodeId);
        List<WorkflowLineLogs> workflowLineLogsList = workflowLineLogsMapper.selectByExample(example);
        if(workflowLineLogsList.isEmpty()){
            return null;
        }
        return workflowLineLogsList.get(0);
    }

    public int updateWorkflowLineLogs(WorkflowLineLogs workflowLineLogs){
        if(workflowLineLogs.getId() == null){
            return 0;
        }
        int result = workflowLineLogsMapper.updateByPrimaryKeySelective(workflowLineLogs);
        return result;
    }


    public int addWorkflowLineLogs(WorkflowLineLogs workflowLineLogs){
        int result = workflowLineLogsMapper.insertSelective(workflowLineLogs);
        return result;
    }


    public int updateIsDefault(String wfNo, Integer type, Long id) {
        Workflow workflow = new Workflow();
        workflow.setIsDefault(0);
        Example example = new Example(Workflow.class);
        Example.Criteria criteria = example.createCriteria();
        if (wfNo == null) {
            criteria.andNotEqualTo("id", id);
        }else {
            criteria.andNotEqualTo("wfNo", wfNo);
        }
        criteria.andEqualTo("type", type);
        int count = workflowMapper.updateByExampleSelective(workflow, example);
        return count;

    }

    public Workflow findWorkflowByWorkflowType(Integer workflowType) {
        Example example = new Example(Workflow.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",workflowType);
        criteria.andEqualTo("isDefault", 1);
        List<Workflow> workflows = workflowMapper.selectByExample(example);
        if (workflows == null || workflows.size() == 0) {
            return null;
        }
        return workflows.get(0);
    }
}
