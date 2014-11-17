package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.OnlineTimeLog;

/**
 * 在线日志DAO接口
 * 
 * @author magicstone
 *
 */
public interface IOnlineTimeLogDao extends ILogDao {
	/**
	 * 查询在线日志
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<OnlineTimeLog> queryOnlineTimeLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
