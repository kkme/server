package com.hifun.soul.tools.deploy;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 脚本执行器
 * 
 * @author crazyjohn
 * 
 */
public class StatementSqlFileExecutor {

	private Logger logger = Logger.getLogger("StatementSqlFileExecutor");

	protected Connection connection;

	public StatementSqlFileExecutor(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 执行指定的sql脚本
	 * 
	 * @param filePath
	 *            sql脚本路径
	 * @throws SQLException
	 *             当执行sql发生错误时候抛出此异常
	 */
	public void executeSqlFile(List<String> sqls) throws SQLException {
		Statement stmt = null;
		boolean commitState = false;
		try {
			commitState = connection.getAutoCommit();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			JDBCUtil.executeSqlCommands(stmt, sqls);
			connection.commit();
			for (String sql : sqls) {
				logger.info("Execute sql : " + sql);
			}
		} catch (Exception e) {
			connection.rollback();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			connection.setAutoCommit(commitState);
		}
	}
}
