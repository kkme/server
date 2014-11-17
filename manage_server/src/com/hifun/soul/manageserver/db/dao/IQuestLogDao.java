package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.QuestLog;

/**
 * 任务日志DAO接口
 * 
 * @author magicstone
 *
 */
public interface IQuestLogDao extends ILogDao {
	/**
	 * 查询任务日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<QuestLog> queryQuestLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
