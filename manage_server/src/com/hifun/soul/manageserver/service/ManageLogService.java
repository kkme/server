package com.hifun.soul.manageserver.service;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.manageserver.entity.OperationLogEntity;
import com.hifun.soul.proto.services.LogServices.AddOperationLogRequest;
import com.hifun.soul.proto.services.LogServices.AddOperationLogResponse;
import com.hifun.soul.proto.services.LogServices.ManageLogRpcService;

public class ManageLogService extends ManageLogRpcService{
	private IDBService manageDbService;

	public ManageLogService(IDBService dbService) {
		this.manageDbService = dbService;
	}
	@Override
	public void addOperationLog(RpcController controller, AddOperationLogRequest request,
			RpcCallback<AddOperationLogResponse> done) {
		Long id = (Long) manageDbService.insert(new OperationLogEntity(request.getOperationLog().toBuilder()));
		boolean result = id > 0 ? true : false;
		done.run(AddOperationLogResponse.newBuilder().setResult(result).build());
	}
}
