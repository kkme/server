package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.LogServices.ChatLog;

/**
 * 聊天日志DAO接口
 * @author magicstone
 *
 */
public interface IChatLogDao extends ILogDao  {
	
	/**
	 * 查询聊天日志
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<ChatLog> queryChatLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
