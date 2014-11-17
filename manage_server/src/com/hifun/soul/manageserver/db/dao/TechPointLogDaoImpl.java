package com.hifun.soul.manageserver.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.manageserver.db.PoolManager;
import com.hifun.soul.proto.services.LogServices.BaseLogInfo;
import com.hifun.soul.proto.services.LogServices.TechPointLog;

public class TechPointLogDaoImpl extends AbstractLogDao implements ITechPointLogDao {
	
	@Override
	protected String getTablePartName(){
		return "tech_point_log";
	}
	
	@Override
	public List<TechPointLog> queryTechPointLogList(Date beginDate, Date endDate, String sqlWhere, int start, int maxCount) {
		List<TechPointLog> resultList = new ArrayList<TechPointLog>();		
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(beginDate);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(endDate);
		int daySpan = TimeUtils.getSoFarWentDays(calBegin, calEnd);
		DateFormat formater = new SimpleDateFormat("yyyy_MM_dd");
		String where = "";
		if (sqlWhere != null && sqlWhere.trim().length() > 0) {
			where = " where " + sqlWhere;
		}
		calBegin.add(Calendar.DAY_OF_MONTH, -1);
		StringBuilder sbUnion = new StringBuilder();
		for (int i = 0; i <= daySpan; i++) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			String dateStr = formater.format(calBegin.getTime());
			String tableName = getTablePartName()+"_" + dateStr;
			if (!isExistsTable(tableName)) {
				continue;
			}
			sbUnion.append("select ");
			sbUnion.append("log_type,");
			sbUnion.append("log_time,");
			sbUnion.append("region_id,");
			sbUnion.append("server_id,");
			sbUnion.append("account_id,");
			sbUnion.append("account_name,");
			sbUnion.append("char_id,");
			sbUnion.append("char_name,");
			sbUnion.append("level,");
			sbUnion.append("alliance_id,");
			sbUnion.append("vip_level,");
			sbUnion.append("reason,");
			sbUnion.append("param,");			
			sbUnion.append("change_num,");
			sbUnion.append("after_change_num,");				
			sbUnion.append("createTime");
			sbUnion.append(" from ");
			sbUnion.append(tableName);
			sbUnion.append(" union all ");
		}
		if(sbUnion.length()==0){
			return resultList;
		}		
		sbUnion.delete(sbUnion.length()-11, sbUnion.length());
		sbUnion.append(") as log ");		
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		sb.append("log_type,");
		sb.append("log_time,");
		sb.append("region_id,");
		sb.append("server_id,");
		sb.append("account_id,");
		sb.append("account_name,");
		sb.append("char_id,");
		sb.append("char_name,");
		sb.append("level,");
		sb.append("alliance_id,");
		sb.append("vip_level,");
		sb.append("reason,");
		sb.append("param,");
		sb.append("change_num,");
		sb.append("after_change_num,");				
		sb.append("createTime");
		sb.append(" from (");
		sb.append(sbUnion.toString());
		sb.append(where);
		sb.append(" limit ");
		sb.append(start);
		sb.append(",");
		sb.append(maxCount);
		String sql = sb.toString();
		logger.debug(sql);		
		Connection conn = PoolManager.getInstance().getConnection(poolName);
		if (conn == null) {
			logger.error("can not connect database...");
			return resultList;
		}
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TechPointLog.Builder logBuilder = TechPointLog.newBuilder();
				BaseLogInfo.Builder baseLogBuilder = BaseLogInfo.newBuilder();
				baseLogBuilder.setLogType(rs.getInt(1));
				baseLogBuilder.setLogTime(rs.getLong(2));
				baseLogBuilder.setRegionId(rs.getInt(3));
				baseLogBuilder.setServerId(rs.getInt(4));
				baseLogBuilder.setAccountId(rs.getLong(5));
				baseLogBuilder.setAccountName(rs.getString(6));
				baseLogBuilder.setCharId(rs.getLong(7));
				baseLogBuilder.setCharName(rs.getString(8));
				baseLogBuilder.setLevel(rs.getInt(9));
				baseLogBuilder.setAllianceId(rs.getInt(10));
				baseLogBuilder.setVipLevel(rs.getInt(11));
				baseLogBuilder.setReason(rs.getInt(12));
				baseLogBuilder.setParam(rs.getString(13));
				logBuilder.setBaseLogInfo(baseLogBuilder.build());				
				logBuilder.setChangeNum(rs.getInt(14));
				logBuilder.setAfterChangeNum(rs.getInt(15));						
				logBuilder.setCreateTime(rs.getLong(16));
				resultList.add(logBuilder.build());
			}
			rs.close();
			stmt.close();
			PoolManager.getInstance().freeConnection(poolName, conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(sql, ex);
			return resultList;
		} finally {
			rs = null;
			stmt = null;
			PoolManager.getInstance().freeConnection(poolName, conn);
		}
		return resultList;
	}	
}
