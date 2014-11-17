package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;
import com.hifun.soul.proto.services.LogServices.FriendLog;

/**
 * 好友日志DAO接口
 * @author magicstone
 *
 */
public interface IFriendLogDao extends ILogDao  {
	/**
	 * 查询好友日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<FriendLog> queryFriendLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
