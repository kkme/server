package com.hifun.soul.manageweb.recharge.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.protobuf.ServiceException;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.common.PagingInfo;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.QQRechargeFlow;
import com.hifun.soul.proto.services.Services.QueryQQRechargeFlowRequest;
import com.hifun.soul.proto.services.Services.QueryQQRechargeFlowResponse;
import com.hifun.soul.proto.services.Services.RechargeRpcService;

public class RechargeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1922617321772062799L;
	
	private List<QQRechargeFlow> qqRechargeFlowList = new ArrayList<QQRechargeFlow>();
	private PagingInfo pagingInfo = new PagingInfo(20);
	private long humanId;
	private String openId;
	private String billno;
	private Date beginTime;
	private Date endTime;
	
	public String queryRechargeFlowForward(){
		return "success";
	}
	
	public String queryRechargeFlow() throws ServiceException{		
		QueryQQRechargeFlowRequest.Builder request = QueryQQRechargeFlowRequest.newBuilder();
		long begin = 0;
		long end = 0;
		if(beginTime!=null){
			begin = TimeUtils.getBeginOfDay(beginTime.getTime());
		}
		if(endTime != null){
			end = TimeUtils.getBeginOfDay(endTime.getTime())+TimeUtils.DAY;
		}
		request.setBeginTime(begin);
		request.setEndTime(end);
		request.setBillno(billno);
		request.setHumanId(humanId);
		request.setOpenId(openId);
		request.setMaxResult(pagingInfo.getPageSize());
		request.setStart(pagingInfo.getPageIndex()*pagingInfo.getPageSize());
		RechargeRpcService.BlockingInterface rechargeService = Managers.getServiceManager().getRechargeService();
		QueryQQRechargeFlowResponse response = rechargeService.queryQQRechargeFlow(null, request.build());
		qqRechargeFlowList.clear();
		if(response.getRechargeFlowList()!=null && response.getRechargeFlowCount()>0){
			qqRechargeFlowList.addAll(response.getRechargeFlowList());
			pagingInfo.setTotalCount(response.getTotalCount());
		}else{
			pagingInfo.setTotalCount(0);
		}
		ManageLogger.log(ManageLogType.QUERY_RECHARGE_FLOW, PermissionType.QUERY_RECHARGE_FLOW, "", response!=null);
		return "success";
	}

	public List<QQRechargeFlow> getQqRechargeFlowList() {
		return qqRechargeFlowList;
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public void validate(){
		if(beginTime==null){
			beginTime=new Date();
		}
		if(endTime==null){
			endTime=new Date();
		}
	}
}
