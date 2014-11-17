package com.hifun.soul.gameserver.common.config;

import java.io.File;

import com.hifun.soul.common.constants.FunctionSwitches;
import com.hifun.soul.core.config.ServerConfig;
import com.hifun.soul.core.server.IMessageProcessor;

/**
 * 服务器配置信息
 * 
 * 一些key/value对 获取资源的路径
 * 
 * 
 */
public class GameServerConfig extends ServerConfig {

	/** 系统配置的数据库版本号 */
	private String dbVersion;
	/** 最大允许在线人数 */
	private int maxOnlineUsers;
	/** 记录统计值开关 */
	private boolean logStatistics = true;
	/* Telnet服务参数定义 */
	/** Telnet服务器名称 */
	private String telnetServerName;
	/** Telnet绑定的ip */
	private String telnetBindIp;
	/** Telnet绑定的端口 */
	private String telnetPort;
	/** 功能开关 */
	private FunctionSwitches funcSwitches = new FunctionSwitches();
	/** 向DB同步的缓存开关 */
	private boolean cacheToDBTurnOn = false;
	/** 多少次tick后向DB同步玩家数据 */
	private int tickTimesToSynchronizationDBForHuman = 1;
	/** 多少次tick后向DB同步其它数据 */
	private int tickTimesToSynchronizationDBForSystem = 1;
	/** 日志服开关 */
	private boolean logServerSwitch = false;
	/** 数据库缓存同步间隔, 单位为毫秒 */
	private long dbSyncInterval = 10000;
	/** 战斗消息处理器的个数 */
	private int battleProcessorCount = Runtime.getRuntime().availableProcessors() + 1;
	private IMessageProcessor mainProcessor;
	/** 新手引导开关 */
	private boolean guideOpened = false;
	/** 登陆墙是否打开，默认关闭 */
	private volatile boolean loginWallEnabled = true;
	/** DB操作记录开关 */
	private boolean dbOperationOpen = true;
	
	// 平台相关的配置
	/** 是否开启平台认证策略 */
	private boolean useLocalAuthorize = false;
	/** 平台类型 (0=不接平台、1=QZone)*/
	private int localType = 0;
	
	
	public GameServerConfig() {
		// Gameserver默认采用的汇报方式
		// getProbeConfig().setProbeReporterMask(
		// ProbeReporter.PREFORMANCE.mark | ProbeReporter.USER.mark);
	}

	/**
	 * 取得资源文件的绝对路径
	 * 
	 * @param pathes
	 *            路径的参数,每个参数将使用路径分隔符连接起来
	 * @return
	 */
	@Override
	public String getResourceFullPath(String... pathes) {
		StringBuilder _sb = new StringBuilder();
		_sb.append(this.getBaseResourceDir());
		for (String _path : pathes) {
			_sb.append(File.separator);
			_sb.append(_path);
		}
		return _sb.toString();
	}

	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * 获得脚本文件路径
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public String getResourceFilePath(String fileName) {
		return this.getResourceFullPath(this.getScriptDir(), fileName);
	}

	/**
	 * @return the maxOnlineUsers
	 */
	public int getMaxOnlineUsers() {
		return maxOnlineUsers;
	}

	/**
	 * @param maxOnlineUsers
	 *            the maxOnlineUsers to set
	 */
	public void setMaxOnlineUsers(int maxOnlineUsers) {
		this.maxOnlineUsers = maxOnlineUsers;
	}

	public boolean isLogStatistics() {
		return logStatistics;
	}

	public void setLogStatistics(boolean logStatistics) {
		this.logStatistics = logStatistics;
	}

	public String getTelnetServerName() {
		return telnetServerName;
	}

	public void setTelnetServerName(String telnetServerName) {
		this.telnetServerName = telnetServerName;
	}

	public String getTelnetBindIp() {
		return telnetBindIp;
	}

	public void setTelnetBindIp(String telnetBindIp) {
		this.telnetBindIp = telnetBindIp;
	}

	public String getTelnetPort() {
		return telnetPort;
	}

	public void setTelnetPort(String telnetPort) {
		this.telnetPort = telnetPort;
	}

	public FunctionSwitches getFuncSwitches() {
		return funcSwitches;
	}

	public boolean isBattleReportFileOutputOn() {
		return funcSwitches.isBattleReportFileOutput();
	}

	public void setBattleReportFileOutputOn(boolean value) {
		funcSwitches.setBattleReportFileOutput(value);
	}

	public boolean isChargeEnabled() {
		return funcSwitches.isChargeEnabled();
	}

	public void setChargeEnabled(boolean value) {
		funcSwitches.setChargeEnabled(value);
	}

	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}

	public String getDbVersion() {
		return dbVersion;
	}

	public boolean isCacheToDBTurnOn() {
		return cacheToDBTurnOn;
	}

	public void setCacheToDBTurnOn(boolean cacheToDBTurnOn) {
		this.cacheToDBTurnOn = cacheToDBTurnOn;
	}

	public boolean isLogServerSwitch() {
		return logServerSwitch;
	}

	public void setLogServerSwitch(boolean logServerSwitch) {
		this.logServerSwitch = logServerSwitch;
	}

	public long getDbSyncInterval() {
		return dbSyncInterval;
	}

	public void setDbSyncInterval(long dbSyncInterval) {
		this.dbSyncInterval = dbSyncInterval;
	}

	public int getBattleProcessorCount() {
		return battleProcessorCount;
	}

	public void setBattleProcessorCount(int battleProcessorCount) {
		this.battleProcessorCount = battleProcessorCount;
	}

	public IMessageProcessor getMainProcessor() {
		return mainProcessor;
	}

	public void setMainProcessor(IMessageProcessor mainProcessor) {
		this.mainProcessor = mainProcessor;
	}

	public boolean getGuideOpened() {
		return guideOpened;
	}

	public void setGuideOpened(boolean guideOpened) {
		this.guideOpened = guideOpened;
	}

	public boolean isLoginWallEnabled() {
		return loginWallEnabled;
	}

	public void setLoginWallEnabled(boolean loginWallEnabled) {
		this.loginWallEnabled = loginWallEnabled;
	}

	public boolean isUseLocalAuthorize() {
		return useLocalAuthorize;
	}

	public void setUseLocalAuthorize(boolean useLocalAuthorize) {
		this.useLocalAuthorize = useLocalAuthorize;
	}

	public int getTickTimesToSynchronizationDBForHuman() {
		return tickTimesToSynchronizationDBForHuman;
	}

	public void setTickTimesToSynchronizationDBForHuman(
			int tickTimesToSynchronizationDBForHuman) {
		this.tickTimesToSynchronizationDBForHuman = tickTimesToSynchronizationDBForHuman;
	}

	public int getTickTimesToSynchronizationDBForSystem() {
		return tickTimesToSynchronizationDBForSystem;
	}

	public void setTickTimesToSynchronizationDBForSystem(
			int tickTimesToSynchronizationDBForSystem) {
		this.tickTimesToSynchronizationDBForSystem = tickTimesToSynchronizationDBForSystem;
	}

	public boolean isDbOperationOpen() {
		return dbOperationOpen;
	}

	public void setDbOperationOpen(boolean dbOperationOpen) {
		this.dbOperationOpen = dbOperationOpen;
	}

	public int getLocalType() {
		return localType;
	}

	public void setLocalType(int localType) {
		this.localType = localType;
	}

}
