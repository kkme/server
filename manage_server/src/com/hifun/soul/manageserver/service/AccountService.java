package com.hifun.soul.manageserver.service;

import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.core.enums.AccountState;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.AccountEntity;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.manageserver.ServerSessionManager;
import com.hifun.soul.manageserver.msg.MGKickOffCharacter;
import com.hifun.soul.proto.services.Services.AccountRpcService;
import com.hifun.soul.proto.services.Services.GetAccountByIdRequest;
import com.hifun.soul.proto.services.Services.GetAccountByIdResponse;
import com.hifun.soul.proto.services.Services.GetAccountByNameRequest;
import com.hifun.soul.proto.services.Services.GetAccountByNameResponse;
import com.hifun.soul.proto.services.Services.GetAccountListRequest;
import com.hifun.soul.proto.services.Services.GetAccountListResponse;
import com.hifun.soul.proto.services.Services.LockAccountRequest;
import com.hifun.soul.proto.services.Services.LockAccountResponse;
import com.hifun.soul.proto.services.Services.UnlockAccountRequest;
import com.hifun.soul.proto.services.Services.UnlockAccountResponse;
import com.hifun.soul.proto.services.Services.UpdateAccountInfoRequest;
import com.hifun.soul.proto.services.Services.UpdateAccountInfoResponse;

/**
 * 账号服务;
 * 
 * @author crazyjohn
 * 
 */
public class AccountService extends AccountRpcService {
	private DBServiceManager dbManager;	
	private ServerSessionManager sessionManager;

	public AccountService(DBServiceManager dbManager,ServerSessionManager sessionManager) {
		this.dbManager = dbManager;
		this.sessionManager = sessionManager;
	}

	@Override
	public void getAccountByName(RpcController controller,
			GetAccountByNameRequest request,
			RpcCallback<GetAccountByNameResponse> done) {
		List<?> entityList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_ACCOUNT_BY_NAME,
				new String[] { "userName" },
				new Object[] { request.getUserName() });
		if (entityList == null || entityList.isEmpty()) {
			done.run(GetAccountByNameResponse.newBuilder().setResult(false)
					.build());
			return;
		}
		AccountEntity entity = (AccountEntity) entityList.get(0);
		done.run(GetAccountByNameResponse.newBuilder().setResult(true)
				.setAccount(entity.getBuilder()).build());
	}

	@Override
	public void getAccountById(RpcController controller,
			GetAccountByIdRequest request,
			RpcCallback<GetAccountByIdResponse> done) {
		AccountEntity entity = this.dbManager.getGameDbService().get(AccountEntity.class,
				request.getId());
		if (entity == null) {
			done.run(GetAccountByIdResponse.newBuilder().setResult(false)
					.build());
			return;
		}
		done.run(GetAccountByIdResponse.newBuilder().setResult(true)
				.setAccount(entity.getBuilder()).build());
	}

	@Override
	public void getAccountList(RpcController controller,
			GetAccountListRequest request,
			RpcCallback<GetAccountListResponse> done) {
		List<?> entityList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_ACCOUNT_LIST, new String[] {},
				new Object[] {}, request.getMaxResult(), request.getStart());
		if (entityList == null || entityList.isEmpty()) {
			done.run(GetAccountListResponse.newBuilder().setTotalCount(0).build());
			return;
		}
		GetAccountListResponse.Builder builder = GetAccountListResponse
				.newBuilder();
		for (int i = 0; i < entityList.size(); i++) {
			AccountEntity entity = (AccountEntity) entityList.get(i);
			builder.addAccount(entity.getBuilder());
		}
		List<?> count = this.dbManager.getGameDbService().findByNamedQuery(DataQueryConstants.QUERY_ACCOUNT_COUNT);
		if (count == null || count.isEmpty()) {
			builder.setTotalCount(0);
		}
		else{
			builder.setTotalCount((Long)count.get(0));
		}
		done.run(builder.build());
	}

	@Override
	public void lockAccount(RpcController controller,
			LockAccountRequest request, RpcCallback<LockAccountResponse> done) {
		this.dbManager.getGameDbService().queryForUpdate(
				DataQueryConstants.UPDATE_ACCOUNT_STATE, new String[] { "id",
						"state" }, new Object[] { request.getPassportId(),
						AccountState.LOCKED.getIndex() });	
		List<?> queryResult = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(DataQueryConstants.QUERY_HUMAN_GUID_BY_PASSPORTID, new String[]{"passportId"},
				new Object[] {request.getPassportId()});
		if(queryResult!=null && queryResult.size()>0){
			for(Object obj : queryResult){
				MGKickOffCharacter mgMsg = new MGKickOffCharacter();
				mgMsg.setHumanGuid((Long)obj);	
				sessionManager.getGameServer().write(mgMsg);	
			}
		}
		done.run(LockAccountResponse.newBuilder().setResult(true).build());
	}

	@Override
	public void unlockAccount(RpcController controller,
			UnlockAccountRequest request,
			RpcCallback<UnlockAccountResponse> done) {
		this.dbManager.getGameDbService().queryForUpdate(
				DataQueryConstants.UPDATE_ACCOUNT_STATE, new String[] { "id",
						"state" }, new Object[] { request.getPassportId(),
						AccountState.NORMAL.getIndex() });
		done.run(UnlockAccountResponse.newBuilder().setResult(true).build());
	}

	@Override
	public void updateAccountInfo(RpcController controller, UpdateAccountInfoRequest request,
			RpcCallback<UpdateAccountInfoResponse> done) {
		this.dbManager.getGameDbService().update(new AccountEntity(request.getAccountInfo().toBuilder()));
		done.run(UpdateAccountInfoResponse.newBuilder().setResult(true).build());
	}
}
