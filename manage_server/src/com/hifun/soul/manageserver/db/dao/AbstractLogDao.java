package com.hifun.soul.manageserver.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.db.PoolManager;

/**
 * 抽象日志相关DAO
 * @author magicstone
 *
 */
public abstract class AbstractLogDao implements ILogDao {
	protected static Logger logger = Loggers.SERVER_LOG_DB_LOGGER;
	protected static String poolName="logServerDB";
	
	/**
	 * 获取数据表前缀
	 * @return
	 */
	protected abstract String getTablePartName();
	
	protected Connection getConnection(){
		return PoolManager.getInstance().getConnection(poolName);
	}
	
	protected void freeConnection(Connection conn){
		PoolManager.getInstance().freeConnection(poolName, conn);
	}
	
	/**
	 * 判断数据库中是否存在指定名称的表
	 * 
	 * @param tableName
	 * @return
	 */
	protected boolean isExistsTable(String tableName){
		String sql="select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='sevensoul_log' and `TABLE_NAME`='"+tableName+"'";
		Connection conn = PoolManager.getInstance().getConnection(poolName);
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				return true;
			}
			rs.close();
			stmt.close();
			PoolManager.getInstance().freeConnection(poolName, conn);
		} catch (SQLException e) {			
			e.printStackTrace();
			logger.error("failed to judge is exists table '"+tableName+"'",e);
		}
		finally{
			PoolManager.getInstance().freeConnection(poolName, conn);
		}
		return false;
	}
	
	/**
	 * 查询记录数量
	 * @param beginDate
	 * @param endDate
	 * @param sqlWhere
	 * @return
	 */
	@Override
	public long queryCount(Date beginDate, Date endDate, String sqlWhere) {		
		long totalCount =0;
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
			sbUnion.append("select id,log_type,log_time,region_id,server_id,account_id,account_name,char_id,char_name,level,alliance_id,vip_level,reason,param from ");
			sbUnion.append(tableName);			
			sbUnion.append(" union all ");
		}
		if(sbUnion.length()==0){
			return totalCount;
		}		
		sbUnion.delete(sbUnion.length()-11, sbUnion.length());
		sbUnion.append(") as log ");
		StringBuilder sb = new StringBuilder();
		sb.append("select count(id) from (");
		sb.append(sbUnion.toString());
		sb.append(where);		
		String sql = sb.toString();
		logger.info(sql);		
		Connection conn = PoolManager.getInstance().getConnection(poolName);
		if (conn == null) {
			logger.error("can not connect database...");
			return totalCount;
		}
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				totalCount = rs.getLong(1);
			}
			rs.close();
			stmt.close();
			PoolManager.getInstance().freeConnection(poolName, conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(sql, ex);
			return totalCount;
		} finally {
			rs = null;
			stmt = null;
			PoolManager.getInstance().freeConnection(poolName, conn);
		}
		return totalCount;
	}	
}
