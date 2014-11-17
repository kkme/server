package com.hifun.soul.manageserver.db.dao;

import java.util.Date;

public interface ILogDao {
	
	/**
	 * 查询记录总数
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	long queryCount(Date beginDate, Date endDate, String sqlWhere);
}
