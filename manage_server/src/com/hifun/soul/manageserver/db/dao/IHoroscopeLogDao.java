package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;
import com.hifun.soul.proto.services.LogServices.HoroscopeLog;

/**
 * 占星日志DAO接口
 * 
 * @author magicstone
 *
 */
public interface IHoroscopeLogDao extends ILogDao  {
	/**
	 * 查询占星日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<HoroscopeLog> queryHoroscopeLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
