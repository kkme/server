package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.TechPointLog;

/**
 * 货币日志Dao接口
 * 
 * @author magicstone
 *
 */
public interface ITechPointLogDao extends ILogDao {
	/**
	 * 查询货币日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<TechPointLog> queryTechPointLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
