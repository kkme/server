package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.PropertyLog;

/**
 * 属性日志DAO接口
 * 
 * @author magicstone
 *
 */
public interface IPropertyLogDao extends ILogDao {
	/**
	 * 查询属性日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<PropertyLog> queryPropertyLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
