package com.hifun.soul.manageserver.db;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.script.ScriptException;

import com.hifun.soul.core.config.ConfigUtil;
import com.hifun.soul.manageserver.db.config.DBConfig;

/**
 * 数据连接池管理器
 * 
 * @author magicstone
 *
 */
public class PoolManager {
	private Vector<DBConfig> dbConfigs = new Vector<DBConfig>();
	private Map<String,ConnectionPool> pools = new HashMap<String,ConnectionPool>();
	private static PoolManager instance= new PoolManager();
	public static PoolManager getInstance(){
		return instance;
	}
	private PoolManager(){
		try {
			loadDrivers();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		for(DBConfig config : dbConfigs){
			CreatePool(config);
		}
	}
	
	private void loadDrivers() throws ScriptException, IOException{
		DBConfig dbConfig = new DBConfig();
		ConfigUtil.loadJsConfig(dbConfig, "log_server_db_config.js");
		dbConfigs.add(dbConfig);
		DBConfig dbConfig2 = new DBConfig();
		ConfigUtil.loadJsConfig(dbConfig2, "manage_server_db_config.js");
		dbConfigs.add(dbConfig2);
	}
	
	private void CreatePool(DBConfig dbConfig){
		ConnectionPool pool = new ConnectionPool(dbConfig);
		pools.put(dbConfig.getPoolName(), pool);
	}
	
	/**
	 * 从连接池中获取数据库连接
	 * @param poolName
	 * @return
	 */
	public Connection getConnection(String poolName){
		ConnectionPool pool = pools.get(poolName);
		if(pool==null){
			return null;
		}
		return pool.getConnection();
	}
	
	/**
	 * 连接池回收数据库连接
	 * @param poolName
	 * @param conn
	 */
	public void freeConnection(String poolName,Connection conn){
		ConnectionPool pool = pools.get(poolName);
		if(pool!=null){
			pool.freeConnection(conn);
		}
	}
	
	public void checkConnection(){
		for(ConnectionPool pool : pools.values()){
			pool.checkConnection();
		}
	}
}
