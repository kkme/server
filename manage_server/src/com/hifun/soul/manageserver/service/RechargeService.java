package com.hifun.soul.manageserver.service;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.QQRechargeEntity;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.proto.services.Services.QQRechargeFlow;
import com.hifun.soul.proto.services.Services.QueryQQRechargeFlowRequest;
import com.hifun.soul.proto.services.Services.QueryQQRechargeFlowResponse;
import com.hifun.soul.proto.services.Services.RechargeRpcService;

public class RechargeService extends RechargeRpcService {
	private DBServiceManager dbManager;
	public RechargeService(DBServiceManager dbManager){
		this.dbManager = dbManager;
	}
	@Override
	public void queryQQRechargeFlow(RpcController controller, QueryQQRechargeFlowRequest request,
			RpcCallback<QueryQQRechargeFlowResponse> done) {
		QueryQQRechargeFlowResponse.Builder response = QueryQQRechargeFlowResponse.newBuilder();
		List<QQRechargeFlow> qqRechargeFlowList = new ArrayList<QQRechargeFlow>();
		if(request.getBillno()!=null && request.getBillno().length()>0){
			//根据billno查
			List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(DataQueryConstants.QUERY_QQ_RECHARGE_BY_BILLNO,
					new String[]{"billno"}, new Object[]{request.getBillno()});
			if(resultList!=null && !resultList.isEmpty()){
				for(Object obj : resultList){
					QQRechargeEntity entity = (QQRechargeEntity)obj;
					qqRechargeFlowList.add(convertEntityToFlow(entity));
				}
			}
		}else{
			//根据humanId查
			if(request.getHumanId()>0){
				List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
						DataQueryConstants.QUERY_QQ_RECHARGE_BY_HUMANID,
						new String[] { "timeBegin","timeEnd","humanId" },
						new Object[] {String.valueOf(request.getBeginTime()),String.valueOf(request.getEndTime()), request.getHumanId() },
						request.getMaxResult(),
						request.getStart());
				if (resultList != null && !resultList.isEmpty()) {
					for (Object obj : resultList) {
						QQRechargeEntity entity = (QQRechargeEntity) obj;
						qqRechargeFlowList.add(convertEntityToFlow(entity));
					}
				}
			}
			//根据openId查
			else if(request.getOpenId()!=null && request.getOpenId().length()>0){
				List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
						DataQueryConstants.QUERY_QQ_RECHARGE_BY_OPENID, 
						new String[] { "timeBegin","timeEnd","openId" },
						new Object[] { String.valueOf(request.getBeginTime()),String.valueOf(request.getEndTime()), request.getOpenId() },
						request.getMaxResult(),
						request.getStart());
				if (resultList != null && !resultList.isEmpty()) {
					for (Object obj : resultList) {
						QQRechargeEntity entity = (QQRechargeEntity) obj;
						qqRechargeFlowList.add(convertEntityToFlow(entity));
					}
				}
			}else{
				List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
						DataQueryConstants.QUERY_QQ_RECHARGE_BY_DATE, 
						new String[] { "timeBegin","timeEnd"},
						new Object[] { String.valueOf(request.getBeginTime()),String.valueOf(request.getEndTime())},
						request.getMaxResult(),
						request.getStart());
				if (resultList != null && !resultList.isEmpty()) {
					for (Object obj : resultList) {
						QQRechargeEntity entity = (QQRechargeEntity) obj;
						qqRechargeFlowList.add(convertEntityToFlow(entity));
					}
				}
			}
		}		
		response.addAllRechargeFlow(qqRechargeFlowList);
		done.run(response.build());

	}
	
	private QQRechargeFlow convertEntityToFlow(QQRechargeEntity entity){
		if(entity==null){
			return null;
		}
		QQRechargeFlow.Builder rechargeFlow = QQRechargeFlow.newBuilder();
		rechargeFlow.setAmt(entity.getAmt());
		rechargeFlow.setBillno(entity.getBillno());
		rechargeFlow.setHumanId(entity.getHumanId());
		rechargeFlow.setId(entity.getId());
		rechargeFlow.setJsonValue(entity.getJsonValue());
		rechargeFlow.setOpenId(entity.getOpenId());
		rechargeFlow.setPayamtCoins(entity.getPayamtCoins());
		rechargeFlow.setPayItem(entity.getPayItem());
		rechargeFlow.setPubacctPayamtCoins(entity.getPubacctPayamtCoins());
		rechargeFlow.setSended(entity.isSended());
		rechargeFlow.setTimeStamp(entity.getTs());
		rechargeFlow.setToken(entity.getToken());
		return rechargeFlow.build();
	}

}
