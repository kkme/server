package com.hifun.soul.core.orm.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.HibernateDBServcieImpl;
import com.hifun.soul.core.orm.HibernateDBServcieImpl.HibernateCallback;

/**
 *
 * 
 */
public class MySqlTableStatusDao {
	private static final String showStatusSql = "show table status like '%s'";

	/**
	 * 取得指定数据库表的状态信息
	 * 
	 * @param dbService
	 * @param tableName
	 * @return
	 */
	public static TableStatus getTableStatus(IDBService dbService, String tableName) {
		final String _sql = String.format(showStatusSql, tableName);
		final TableStatus _status = new TableStatus();
		HibernateDBServcieImpl _hibernate = (HibernateDBServcieImpl) dbService;
		_hibernate.call(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session session) {
				session.doWork(new Work() {
					@Override
					public void execute(Connection connection) throws SQLException {
						Statement _statement = connection.createStatement();
						ResultSet _resultSet = _statement.executeQuery(_sql);
						if (_resultSet.next()) {
							_status.nextId = _resultSet.getLong("Auto_increment");
						}
					}
				});
				return null;
			}
		});
		return _status;
	}

	/**
	 * 数据库表的状态信息
	 * 
	  *
	 * 
	 */
	public static final class TableStatus {
		/**
		 * 下一个ID,默认值是-1,即无效的id
		 */
		public long nextId = -1;
	}
}
