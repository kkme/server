package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.ItemLog;

/**
 * 物品日志DAO接口
 * 
 * @author magicstone
 *
 */
public interface IItemLogDao extends ILogDao {
	/**
	 * 查询物品日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<ItemLog> queryItemLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
