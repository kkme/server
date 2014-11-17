package com.hifun.soul.manageserver.db.config;

import com.hifun.soul.core.config.Config;

/**
 * 数据库配置
 * @author magicstone
 *
 */
public class DBConfig implements Config {
	/** 数据库类型 */
	private String dbType="";
	/** 数据库连接池名称 */
	private String poolName="";
	/** 数据库连接url */
	private String url="";
	/** 数据库名 */
	private String dbName="";
	/** 用户名 */
	private String user="";
	/** 密码 */
	private String password="";
	/** 数据库驱动 */
	private String driver = "";	
	/** 最大连接数 */
	private int maxConnectionCount=1;	
	/** 初始化连接池个数 */
	private int initialSize=1;
	/** 连接过期时间 */
	private int connctionTimeOut=3*60*1000;
	
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver(){
		return driver;
	}
	public void setDriver(String driver){
		this.driver = driver;
	}

	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName){
		this.dbName = dbName;
	}	
	public int getMaxConnectionCount() {
		return maxConnectionCount;
	}
	public void setMaxConnectionCount(int maxConnectionCount) {
		this.maxConnectionCount = maxConnectionCount;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getConnctionTimeOut() {
		return connctionTimeOut;
	}
	public void setConnctionTimeOut(int connctionTimeOut) {
		this.connctionTimeOut = connctionTimeOut;
	}
	@Override
	public String getVersion() {		
		return "";
	}
	@Override
	public void validate() {
				
	}
	@Override
	public boolean getIsDebug() {
		return false;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
}
