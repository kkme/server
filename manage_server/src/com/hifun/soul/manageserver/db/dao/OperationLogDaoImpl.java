package com.hifun.soul.manageserver.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.db.PoolManager;
import com.hifun.soul.proto.services.LogServices.OperationLog;

public class OperationLogDaoImpl implements IOperationLogDao {

	protected static Logger logger = Loggers.SERVER_LOG_DB_LOGGER;
	protected static String poolName="manageServerDB";
	protected String tableName="t_operation_log";

	@Override
	public List<OperationLog> queryOperationLogList(long beginTime, long endTime, String sqlWhere, int start,
			int maxCount) {
		List<OperationLog> logList = new ArrayList<OperationLog>();		
		StringBuilder sbSqlWhere = new StringBuilder();
		sbSqlWhere.append(" where operateTime<");
		sbSqlWhere.append(endTime);
		sbSqlWhere.append(" and operateTime>");
		sbSqlWhere.append(beginTime);
		if(sqlWhere != null && sqlWhere.length()>0){
			sbSqlWhere.append(" and ");
			sbSqlWhere.append(sqlWhere);
		}
		sbSqlWhere.append(" limit ");
		sbSqlWhere.append(start);
		sbSqlWhere.append(",");
		sbSqlWhere.append(maxCount);
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select id,userId,userName,operationType,operationText,hasPermission,param,result,operateTime from ");
		sbSql.append(tableName);
		sbSql.append(sbSqlWhere.toString());
		String sql = sbSql.toString();
		logger.debug(sql);		
		Connection conn = PoolManager.getInstance().getConnection(poolName);
		if (conn == null) {
			logger.error("can not connect database...");
			return logList;
		}
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				OperationLog.Builder logBuilder = OperationLog.newBuilder();				
				logBuilder.setId(rs.getLong(1));
				logBuilder.setUserId(rs.getInt(2));
				logBuilder.setUserName(rs.getString(3));
				logBuilder.setOperationType(rs.getInt(4));
				logBuilder.setOperationText(rs.getString(5));
				logBuilder.setHasPermission(rs.getBoolean(6));
				logBuilder.setParam(rs.getString(7));
				logBuilder.setResult(rs.getBoolean(8));
				logBuilder.setOperateTime(rs.getLong(9));				
				logList.add(logBuilder.build());
			}
			rs.close();
			stmt.close();
			PoolManager.getInstance().freeConnection(poolName, conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(sql, ex);
			return logList;
		} finally {
			rs = null;
			stmt = null;
			PoolManager.getInstance().freeConnection(poolName, conn);
		}
		return logList;
	}
	
	@Override
	public int queryCount(long beginTime, long endTime, String sqlWhere){
		int count = 0;
		StringBuilder sbSqlWhere = new StringBuilder();
		sbSqlWhere.append(" where operateTime<");
		sbSqlWhere.append(endTime);
		sbSqlWhere.append(" and operateTime>");
		sbSqlWhere.append(beginTime);
		if(sqlWhere != null && sqlWhere.length()>0){
			sbSqlWhere.append(" and ");
			sbSqlWhere.append(sqlWhere);
		}
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select count(id) from ");
		sbSql.append(tableName);
		sbSql.append(sbSqlWhere.toString());
		String sql = sbSql.toString();
		logger.debug(sql);		
		Connection conn = PoolManager.getInstance().getConnection(poolName);
		if (conn == null) {
			logger.error("can not connect database...");
			return -1;
		}
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);			
		}catch(SQLException ex) {
			ex.printStackTrace();
			logger.error(sql, ex);
			return -1;
		} finally {
			rs = null;
			stmt = null;
			PoolManager.getInstance().freeConnection(poolName, conn);
		}
		return count;
	}

}
