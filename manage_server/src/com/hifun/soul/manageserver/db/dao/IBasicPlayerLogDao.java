package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.BasicPlayerLog;


/**
 * 玩家日志DAO
 * @author magicstone
 *
 */
public interface IBasicPlayerLogDao extends ILogDao {
	
	/**
	 * 查询玩家日志
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<BasicPlayerLog> queryBasicPlayerLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);	
	
}
