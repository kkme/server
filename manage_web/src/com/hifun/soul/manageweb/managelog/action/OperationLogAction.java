package com.hifun.soul.manageweb.managelog.action;

import java.util.Date;
import java.util.List;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.common.PagingInfo;
import com.hifun.soul.proto.services.LogServices.LogRpcService;
import com.hifun.soul.proto.services.LogServices.OperationLog;
import com.hifun.soul.proto.services.LogServices.QueryOperationLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryOperationLogResponse;

public class OperationLogAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5342926261654722396L;

	private Date beginDate;
	private Date endDate;
	private int userId;	
	private String userName;
	private int operationType;	
	private PagingInfo pagingInfo = new PagingInfo(20);
	private List<OperationLog> operationLogs;
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}
	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}
	public List<OperationLog> getOperationLogs() {
		return operationLogs;
	}
	public void setOperationLogs(List<OperationLog> operationLogs) {
		this.operationLogs = operationLogs;
	}
	
	public String queryOperationLogList() throws ServiceException{
		LogRpcService.BlockingInterface serverLogService = Managers.getServiceManager().getServerLogService();
	    QueryOperationLogRequest.Builder request  = QueryOperationLogRequest.newBuilder();
	    request.setBeginDate(beginDate.getTime());
	    request.setEndDate(endDate.getTime());
	    request.setUserId(userId);
	    request.setUserName(userName);
	    request.setOperationType(operationType);	    
	    request.setStart(pagingInfo.getPageIndex()*pagingInfo.getPageSize());
	    request.setMaxResult(pagingInfo.getPageSize());
	    QueryOperationLogResponse response = serverLogService.queryOperationLog(null, request.build());
	    operationLogs = response.getOperationLogsList();
	    pagingInfo.setTotalCount(response.getTotalCount());
		return "success";
	}
	
	@Override
	public void validate(){
		if(beginDate==null){
			beginDate=new Date();
		}
		if(endDate==null){
			endDate=new Date();
		}
		if(userName==null){
			userName="";
		}
	}
}
