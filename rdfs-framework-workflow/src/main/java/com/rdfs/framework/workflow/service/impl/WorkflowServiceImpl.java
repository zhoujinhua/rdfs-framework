package com.rdfs.framework.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.utils.AuthUtil;
import com.rdfs.framework.core.utils.StringUtils;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwRunExecution;
import com.rdfs.framework.workflow.entity.CwRunTask;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.NodeEventService;
import com.rdfs.framework.workflow.service.ProcessInfoService;
import com.rdfs.framework.workflow.service.RunExecutionService;
import com.rdfs.framework.workflow.service.RunTaskService;
import com.rdfs.framework.workflow.service.TaskMonitorService;
import com.rdfs.framework.workflow.service.TaskNodeService;
import com.rdfs.framework.workflow.service.WorkflowService;

@Service
public class WorkflowServiceImpl extends HibernateServiceSupport implements WorkflowService {

	@Autowired
	private NodeEventService nodeEventService;
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private RunExecutionService runExecutionService;
	@Autowired
	private RunTaskService runTaskService;
	@Autowired
	private TaskMonitorService taskMonitorService;
	@Autowired
	private TaskNodeService taskNodeService;
	
	@Override
	public void claimTask(String businessKey, String flowName, String userId){
		CwRunTask runTask = runTaskService.getRunTask(businessKey, flowName);
		if(!StringUtils.isBlank(userId)){
			runTask.setAssigenee(userId);
		} else {
			throw new RuntimeException("必须指定领单人.");
		}
	}
	
	@Override
	public void completeTask(String businessKey, String flowName, String action) {
		CwRunTask runTask = runTaskService.getRunTask(businessKey, flowName);
		CwNodeEvent nodeEvent = null; //当前事件
		if(StringUtils.isBlankObj(runTask)){
			CwProcessInfo processInfo = processInfoService.getLastProcess(flowName);
			if(StringUtils.isBlankObj(processInfo)){
				throw new RuntimeException("流程定义不存在.");
			}
			CwTaskNode start = taskNodeService.getStartNode(processInfo);
			nodeEvent = nodeEventService.getNodeEvent(start, action);
			
			runTask.setBusinessKey(businessKey);
			runTask.setProcessInfo(processInfo);
			runTask.setTaskNode(nodeEvent.getNextNode());
			runTask.setCreateTime(new Date());
		} else {
			nodeEvent = nodeEventService.getNodeEvent(runTask.getTaskNode(), action);
			if(StringUtils.isBlankObj(nodeEvent)){
				throw new RuntimeException("事件定义不存在.");
			}
			runTask.setTaskNode(nodeEvent.getNextNode());
		}
		if(runTask.getTaskNode().getType().equals("end")){
			runTask.setStatus(Constants.IS.YES);
		} else {
			runTask.setStatus(Constants.IS.NO);
		}
		if(!StringUtils.isBlank(nodeEvent.getUser()) && StringUtils.match("\\$\\{(.*?)}", nodeEvent.getUser())){
			String code = nodeEvent.getUser().substring(2,nodeEvent.getUser().length()-1);
			CwRunExecution runExecution = runExecutionService.getRunExecution(businessKey, flowName, code);
			if(!StringUtils.isBlankObj(runExecution)){
				runTask.setAssigenee(runExecution.getUserId());
			}
		}
		
		
		CwRunExecution runExecution = new CwRunExecution();
		runExecution.setBusinessKey(businessKey);
		runExecution.setCreateTime(new Date());
		runExecution.setNodeEvent(nodeEvent);
		runExecution.setProcessInfo(runTask.getProcessInfo());
		runExecution.setUserId(AuthUtil.getCurrentUserDto().getUserId());
		
		mergeEntity(runTask);
		saveEntity(runExecution);
		try{
			taskMonitorService.invoke(runTask, nodeEvent, "complete");
		} catch(Exception e){
			throw new RuntimeException("唤醒事件监听失败,",e);
		}
	}
	
	@Override
	public String getNextRoute(String businessKey, String flowName, String action){
		CwRunTask runTask = runTaskService.getRunTask(businessKey, flowName);
		CwNodeEvent nodeEvent = null;
		if(StringUtils.isBlankObj(runTask)){
			CwProcessInfo processInfo = processInfoService.getLastProcess(flowName);
			CwTaskNode start = taskNodeService.getStartNode(processInfo);
			nodeEvent = nodeEventService.getNodeEvent(start, action);
		} else {
			nodeEvent = nodeEventService.getNodeEvent(runTask.getTaskNode(), action);
		}
		return nodeEvent.getRoute();
	}
	
	public List<CwProcessInfo> getCwProcessInfos(){
		return processInfoService.getProcessInfos();
	}
	
	@Override
	public CwProcessInfo getProcessInfo(Integer id){
		return getEntityById(CwProcessInfo.class, id, false);
	}
}
