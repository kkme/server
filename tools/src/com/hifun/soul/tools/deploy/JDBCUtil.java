package com.hifun.soul.tools.deploy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * JDBC工具
 * 
 * @author crazyjohn
 * 
 */
public class JDBCUtil {
	/**
	 * 获取数据库连接
	 * 
	 * @param url
	 *            连接url
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws ClassNotFoundException
	 *             找不到数据库驱动抛出此异常
	 * @throws SQLException
	 *             获取数据库连接失败抛出此异常
	 */
	public static Connection getConnection(String url, String userName,
			String password) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, userName, password);
		return connection;
	}

	/**
	 * 执行指定的查询select语句
	 * 
	 * @param stmt
	 *            传入的Statement对象
	 * @param querySql
	 *            查询语句
	 * @return 返回查询结果集
	 * @throws SQLException
	 *             如果执行错误抛出此异常
	 */
	public static ResultSet query(Statement stmt, String querySql)
			throws SQLException {
		ResultSet rs = stmt.executeQuery(querySql);
		return rs;
	}

	/**
	 * 执行指定的sql命令集
	 * 
	 * @param stmt
	 * 
	 * @param sqls
	 *            sql命令集
	 * @throws SQLException
	 *             如果执行错误抛出此异常
	 */
	public static void executeSqlCommands(Statement stmt,
			Collection<String> sqls) throws SQLException {
		for (String command : sqls) {
			stmt.addBatch(command);
		}
		stmt.executeBatch();
	}
}
