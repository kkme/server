package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.SkillPointLog;

/**
 * 货币日志Dao接口
 * 
 * @author magicstone
 *
 */
public interface ISkillPointLogDao extends ILogDao {
	/**
	 * 查询货币日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<SkillPointLog> querySkillPointLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
