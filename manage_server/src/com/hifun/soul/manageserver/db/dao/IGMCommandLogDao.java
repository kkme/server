package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;
import com.hifun.soul.proto.services.LogServices.GMCommandLog;

/**
 * GM命令日志DAO接口
 * @author magicstone
 *
 */
public interface IGMCommandLogDao extends ILogDao  {
	/**
	 * 查询GM命令日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<GMCommandLog> queryGMCommandLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
