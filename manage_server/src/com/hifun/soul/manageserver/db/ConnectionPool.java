package com.hifun.soul.manageserver.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.db.config.DBConfig;

public class ConnectionPool {
	private static Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	private static final int TRY_GET_CONNECTE_TIME = 3;
	private List<PooledConnection> pooledConnections = new ArrayList<PooledConnection>();
	private int isUsedNum;
	DBConfig dbConfig = null;

	public ConnectionPool(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	/**
	 * 从数据池中获取数据库连接
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection conn = getFreeConnection();
		int tryTimes = 0;
		while (conn == null) {
			if (tryTimes >= TRY_GET_CONNECTE_TIME) {
				break;
			}
			tryTimes++;
			waitTime(1000);
			conn = getFreeConnection();			
		}
		if (conn == null) {
			logger.error("从连接池" + this.dbConfig.getPoolName() + "中获取连接失败。");
		} else {
			isUsedNum++;
			logger.info("得到　" + this.dbConfig.getPoolName() + "　的连接，现有" + isUsedNum + "个连接在使用!");
		}
		return conn;
	}

	/**
	 * 获取可用连接
	 * 
	 * @return
	 */
	private Connection getFreeConnection() {
		Connection conn = findFreeConnection();
		if (conn == null) {
			conn = createConnection();
		}
		return conn;
	}

	/**
	 * 从连接池中查找可用连接
	 * 
	 * @return
	 */
	private Connection findFreeConnection() {
		Connection conn = null;
		List<PooledConnection> closedConns = new ArrayList<PooledConnection>();
		for (PooledConnection pConn : pooledConnections) {
			if (!pConn.isBusy()) {
				conn = pConn.getConnection();
				try {
					if (conn.isClosed()) {
						logger.info("从连接池" + this.dbConfig.getPoolName() + "中获取的连接已关闭,重新获取连接");
						closedConns.add(pConn);
						continue;
					}
				} catch (SQLException e) {
					logger.info("从连接池" + this.dbConfig.getPoolName() + "中获取连接发生服务器访问错误,重新获取连接");
					continue;					
				}
				pConn.setBusy(true);
				break;
			}
		}
		pooledConnections.removeAll(closedConns);
		return conn;
	}

	/**
	 * 创建连接对象
	 */
	private Connection createConnection() {
		Connection conn = null;
		logger.info(dbConfig.getPoolName()+" getInitialSize:"+dbConfig.getInitialSize());
		for (int i = 0; i < dbConfig.getInitialSize(); i++) {
			if (pooledConnections.size() == dbConfig.getMaxConnectionCount()) {
				break;
			}
			conn = newConnection();
			if (conn != null) {
				PooledConnection pConn = new PooledConnection(conn);
				pConn.setBusy(true);
				this.pooledConnections.add(pConn);
			}
		}
		return conn;
	}

	/**
	 * 新建一个数据库连接
	 * 
	 * @return
	 */
	private synchronized Connection newConnection() {
		Connection conn = null;
		try {
			Class.forName(dbConfig.getDriver());
			conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		return conn;
	}

	/**
	 * 用完后归还连接池
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void freeConnection(Connection conn) {
		if (conn == null) {			
			return;
		}		
		for (PooledConnection pConn : this.pooledConnections) {
			if (conn == pConn.getConnection() && pConn.isBusy() == true) {
				pConn.setBusy(false);
				isUsedNum--;				
				break;
			}
		}
	}

	/**
	 * 释放所有连接
	 */
	public void releaseAll() {
		for (int i = pooledConnections.size() - 1; i >= 0; i--) {
			try {
				pooledConnections.get(i).getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pooledConnections.remove(i);
		}
		isUsedNum = 0;
	}

	/**
	 * 使程序等待一定毫秒数
	 * 
	 * @param sleepTime
	 */
	private void waitTime(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 检查db连接
	 */
	public void checkConnection() {
		Connection conn = this.getConnection();
		if (conn == null) {
			return;
		}
		Statement pingStatement;
		try {
			pingStatement = (Statement) conn.createStatement();
			pingStatement.executeQuery("SELECT 1").close();// 测试是否超时的另一种做法
			pingStatement.close();
			this.freeConnection(conn);
			logger.info("check database connection success");
		} catch (SQLException e) {
			logger.error("check database connection error", e);
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 内部使用的用于保存连接池中连接对象的类
	 * 
	 * 此类中有两个成员，一个是数据库的连接，另一个是指示此连接是否
	 * 
	 * 正在使用的标志。
	 * 
	 */
	class PooledConnection {

		Connection connection = null;// 数据库连接
		boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用

		// 构造函数，根据一个 Connection 构告一个 PooledConnection 对象
		public PooledConnection(Connection connection) {
			this.connection = connection;
		}

		// 返回此对象中的连接
		public Connection getConnection() {
			return connection;
		}

		// 设置此对象的，连接
		public void setConnection(Connection connection) {
			this.connection = connection;
		}

		// 获得对象连接是否忙
		public boolean isBusy() {
			return busy;
		}

		// 设置对象的连接正在忙
		public void setBusy(boolean busy) {
			this.busy = busy;
		}

	}

}
