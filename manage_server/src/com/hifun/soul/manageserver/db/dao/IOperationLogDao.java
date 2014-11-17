package com.hifun.soul.manageserver.db.dao;

import java.util.List;

import com.hifun.soul.proto.services.LogServices.OperationLog;

public interface IOperationLogDao {


	public List<OperationLog> queryOperationLogList(long beginDate, long endDate, String sqlWhere, int start, int maxCount);

	int queryCount(long beginTime, long endTime, String sqlWhere);
}
