package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.RechargeLog;

public interface IRechargeLogDao extends ILogDao {
	/**
	 * 查询任务日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<RechargeLog> queryRechargeLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
