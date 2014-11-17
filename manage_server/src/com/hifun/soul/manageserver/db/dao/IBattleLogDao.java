package com.hifun.soul.manageserver.db.dao;

import java.util.Date;
import java.util.List;
import com.hifun.soul.proto.services.LogServices.BattleLog;

/**
 * 战斗日志DAO接口
 * @author magicstone
 *
 */
public interface IBattleLogDao  extends ILogDao {
	/**
	 * 查询战斗日志
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	List<BattleLog> queryBattleLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount);
}
