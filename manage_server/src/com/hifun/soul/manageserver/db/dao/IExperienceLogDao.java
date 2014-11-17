package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.ExperienceLog;

/**
 * 货币日志Dao接口
 * 
 * @author magicstone
 *
 */
public interface IExperienceLogDao extends ILogDao {
	/**
	 * 查询货币日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<ExperienceLog> queryExperienceLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
