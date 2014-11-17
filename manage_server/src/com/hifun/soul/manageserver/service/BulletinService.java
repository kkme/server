package com.hifun.soul.manageserver.service;

import java.util.List;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.BulletinEntity;
import com.hifun.soul.manageserver.msg.MGBulletinAdd;
import com.hifun.soul.manageserver.msg.MGBulletinRemove;
import com.hifun.soul.manageserver.msg.MGBulletinUpdate;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.ServerSessionManager;
import com.hifun.soul.proto.services.Services.AddBulletinRequest;
import com.hifun.soul.proto.services.Services.AddBulletinResponse;
import com.hifun.soul.proto.services.Services.Bulletin;
import com.hifun.soul.proto.services.Services.BulletinRpcService;
import com.hifun.soul.proto.services.Services.GetBulletinListRequest;
import com.hifun.soul.proto.services.Services.GetBulletinListResponse;
import com.hifun.soul.proto.services.Services.RemoveBulletinRequest;
import com.hifun.soul.proto.services.Services.RemoveBulletinResponse;
import com.hifun.soul.proto.services.Services.UpdateBulletinRequest;
import com.hifun.soul.proto.services.Services.UpdateBulletinResponse;

public class BulletinService extends BulletinRpcService {

	private ServerSessionManager sessionManager;
	private RpcCallbackManager callbackManager;
	private DBServiceManager dbManager;

	public BulletinService(DBServiceManager dbManager,ServerSessionManager sessionManager,RpcCallbackManager callbackManager) {
		this.dbManager = dbManager;
		this.sessionManager = sessionManager;
		this.callbackManager = callbackManager;
	}

	@Override
	public void addBulletin(RpcController controller, AddBulletinRequest request, RpcCallback<AddBulletinResponse> done) {
		MGBulletinAdd bulletinAddMsg = new MGBulletinAdd();
		bulletinAddMsg.setBulletin(request.getBulletin());	
		callbackManager.registerCallback(bulletinAddMsg, done);
		sessionManager.getGameServer().write(bulletinAddMsg);
	}

	@Override
	public void updateBulletin(RpcController controller, UpdateBulletinRequest request,
			RpcCallback<UpdateBulletinResponse> done) {
		MGBulletinUpdate bulletinUpdateMsg = new MGBulletinUpdate();
		bulletinUpdateMsg.setBulletin(request.getBulletin());		
		callbackManager.registerCallback(bulletinUpdateMsg, done);
		sessionManager.getGameServer().write(bulletinUpdateMsg);

	}

	@Override
	public void removeBulletin(RpcController controller, RemoveBulletinRequest request,
			RpcCallback<RemoveBulletinResponse> done) {
		MGBulletinRemove bulletinRemoveMsg = new MGBulletinRemove();
		bulletinRemoveMsg.setBulletinId(request.getBulletinId());
		callbackManager.registerCallback(bulletinRemoveMsg, done);
		sessionManager.getGameServer().write(bulletinRemoveMsg);
	}

	@Override
	public void getBulletinList(RpcController controller, GetBulletinListRequest request,
			RpcCallback<GetBulletinListResponse> done) {
		String contentKey = "%%";
		if (request.getContentKey() != null && request.getContentKey().length() > 0) {
			contentKey = "%" + request.getContentKey() + "%";
		}
		String queryName=DataQueryConstants.QUERY_BULLETIN_LIST_BY_CONDITION;
		String[] paramNames = new String[] { "valid", "content" };
		Object[] paramValues = new Object[] { request.getValidState()!=0,contentKey };
		if(request.getValidState()==-1){
			queryName=DataQueryConstants.QUERY_ALL_BULLETIN_LIST_BY_CONDITION;
			paramNames =  new String[] { "content" };
			paramValues = new Object[] {  contentKey };
		}
		List<?> entityList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				queryName,
				paramNames,
				paramValues, 
				request.getMaxResult(),
				request.getStart());
		if (entityList == null || entityList.isEmpty()) {
			done.run(GetBulletinListResponse.newBuilder().setTotalCount(0).build());
			return;
		}
		GetBulletinListResponse.Builder builder = GetBulletinListResponse.newBuilder();
		for (int i = 0; i < entityList.size(); i++) {
			BulletinEntity entity = (BulletinEntity) entityList.get(i);
			Bulletin bulletin = getBulletinFromEntity(entity);
			builder.addBulletins(bulletin);
		}
		String queryCountName=DataQueryConstants.QUERY_BULLETIN_COUNT_BY_CONDITION;
		String[] paramCountNames = new String[] { "valid", "content" };
		Object[] paramCountValues = new Object[] { request.getValidState()!=0,contentKey };
		if(request.getValidState()==-1){
			queryCountName=DataQueryConstants.QUERY_ALL_BULLETIN_COUNT_BY_CONDITION;
			paramCountNames =  new String[] { "content" };
			paramCountValues = new Object[] {  contentKey };
		}
		
		List<?> resultList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				queryCountName,
				paramCountNames,
				paramCountValues);
		builder.setTotalCount((Long)resultList.get(0));
		done.run(builder.build());

	}

	/**
	 * 将实体转换为传输对象
	 * 
	 * @param basicEntity
	 * @param periodicalEntity
	 * @return
	 */
	private Bulletin getBulletinFromEntity(BulletinEntity entity) {
		Bulletin.Builder builder = Bulletin.newBuilder();
		builder.setId(entity.getId());
		builder.setBulletinType(entity.getBulletinType());
		builder.setContent(entity.getContent());
		if (entity.getPublishTime() != null) {
			builder.setPublishTime(entity.getPublishTime().getTime());
		}
		builder.setShowTime(entity.getShowTime());
		builder.setLevel(entity.getLevel());
		builder.setValid(entity.isValid());
		if (entity.getStartDate() != null) {
			builder.setStartDate(entity.getStartDate().getTime());
		}
		if (entity.getEndDate() != null) {
			builder.setEndDate(entity.getEndDate().getTime());
		}
		if (entity.getStartTime() != null) {
			builder.setStartTime(entity.getStartTime().getTime());
		}
		if (entity.getEndTime() != null) {
			builder.setEndTime(entity.getEndTime().getTime());
		}
		if (entity.getLastPublishTime() != null) {
			builder.setLastPublishTime(entity.getLastPublishTime().getTime());
		}
		if(entity.getCreateTime() !=null){
			builder.setCreateTime(entity.getCreateTime().getTime());
		}
		builder.setPublishInterval(entity.getPublishInterval());
		return builder.build();
	}
}
