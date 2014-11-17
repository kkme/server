package com.hifun.soul.manageweb.serverlog.action;

import java.util.Date;
import java.util.List;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.common.PagingInfo;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.LogServices.MoneyLog;
import com.hifun.soul.proto.services.LogServices.LogRpcService;
import com.hifun.soul.proto.services.LogServices.QueryMoneyLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryMoneyLogResponse;

public class MoneyLogAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date beginDate;
	private Date endDate;
	private int serverId;	
	private long characterId;
	private String characterName;
	private long accountId;
	private String accountName;
	private PagingInfo pagingInfo = new PagingInfo(20);
	private List<MoneyLog> moneyLogs;
	private int logReason;
	private int currencyType;
	
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
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public List<MoneyLog> getMoneyLogs() {
		return moneyLogs;
	}

	public void setMoneyLogs(List<MoneyLog> moneyLogs) {
		this.moneyLogs = moneyLogs;
	}

	public int getLogReason() {
		return logReason;
	}

	public void setLogReason(int logReason) {
		this.logReason = logReason;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public String queryMoneyLogList() throws ServiceException{
	    LogRpcService.BlockingInterface serverLogService = Managers.getServiceManager().getServerLogService();
	    QueryMoneyLogRequest.Builder request  = QueryMoneyLogRequest.newBuilder();
	    ManageLogger.log(ManageLogType.QUERY_MONEY_LOG_LIST, PermissionType.QUERY_MONEY_LOG_LIST, "", true);
	    request.setBeginDate(beginDate.getTime());
	    request.setEndDate(endDate.getTime());
	    request.setAccountId(accountId);
	    request.setAccountName(accountName);
	    request.setCharacterId(characterId);
	    request.setCharacterName(characterName);
	    request.setServerId(serverId);
	    request.setStart(pagingInfo.getPageIndex()*pagingInfo.getPageSize());
	    request.setMaxResult(pagingInfo.getPageSize());
	    request.setReason(logReason);
	    request.setCurrencyType(currencyType);
	    QueryMoneyLogResponse response = serverLogService.queryMoneyLog(null, request.build());
	    moneyLogs = response.getMoneyLogsList();
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
		if(accountName==null){
			accountName="";
		}
		if(characterName==null){
			characterName="";
		}
	}
}
