package com.hifun.soul.gameserver;

import java.io.IOException;

import javax.script.ScriptException;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.config.ConfigUtil;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.context.PackagePathType;
import com.hifun.soul.core.msg.recognizer.IMessageRecognizer;
import com.hifun.soul.core.orm.DBConfiguration;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.schedule.IScheduleService;
import com.hifun.soul.core.schedule.ScheduleServiceImpl;
import com.hifun.soul.core.server.AbstractIoHandler;
import com.hifun.soul.core.server.CommonMessageRecognizer;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.core.server.ServerProcess;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.core.uuid.UUIDService;
import com.hifun.soul.core.uuid.UUIDType;
import com.hifun.soul.gamedb.IDBDispatcher;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gamedb.config.DBOperationConfig;
import com.hifun.soul.gamedb.config.DBServiceManager;
import com.hifun.soul.gamedb.msg.handler.DBMessageHandler;
import com.hifun.soul.gamedb.service.DataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.GlobalHeartbeatObjectManager;
import com.hifun.soul.gameserver.common.config.GameServerConfig;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.startup.GameExecutorService;
import com.hifun.soul.gameserver.startup.GameMessageProcessor;
import com.hifun.soul.gameserver.startup.GameServerIoHandler;
import com.hifun.soul.gameserver.startup.GameServerRuntime;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 游戏服务器入口
 * 
 * @author crazyjohn
 * 
 */
public class GameServer {

	private static final Logger logger = Loggers.GAME_LOGGER;
	/** 服务器公共配置信息 */
	private GameServerConfig config;
	/** 平台配置 */
	private LocalConfig localConfig = new LocalConfig();
	/** 服务器进程 */
	private ServerProcess serverProcess;
	/** gm后台进程 */
	private ServerProcess manageProcess;
	/** 数据服务管理器 */
	private DBServiceManager dbServiceManager;

	/** 主消息处理器 */
	private GameMessageProcessor mainMsgProcessor;
	/** 管理服消息处理器 */
	private QueueMessageProcessor manageMsgProcessor;
	/** 全局心跳对象管理器 */
	private GlobalHeartbeatObjectManager globalHeartbeatObjectManager;
	/** 数据服务执行器 */
	private IDBDispatcher dbExecutorService;

	private GameServer(String cfgPath) throws ScriptException, IOException {
		config = new GameServerConfig();
		ConfigUtil.loadJsConfig(config, cfgPath);
		// 把服务器配置注入
		ApplicationContext
				.getApplicationContext()
				.getDefaultListableBeanFactory()
				.registerSingleton(GameServerConfig.class.getSimpleName(),
						this.config);
		// 初始化平台配置;
		if (config.isUseLocalAuthorize()) {
			ConfigUtil.loadJsConfig(localConfig, "local.cfg.js");
		}
	}

	private void init() throws Exception {
		// 初始化DI容器上下文
		ApplicationContext.initAndScan(PackagePathType.PACKAGE_GAME_SERVER
				.getPackagePaths());
		// 主消息处理器
		mainMsgProcessor = new GameMessageProcessor(
				config.getBattleProcessorCount());
		config.setMainProcessor(mainMsgProcessor);
		// 初始化一些基础服务
		GameServerAssist.init(config, localConfig);

		// 初始化服务器进程
		IMessageRecognizer msgRecognizer = new CommonMessageRecognizer();

		GameExecutorService executorService = ApplicationContext
				.getApplicationContext().getBean(GameExecutorService.class);
		// 对外服务io处理器;
		AbstractIoHandler<MinaGameClientSession> externalIoHandler = new GameServerIoHandler(
				config.getFlashSocketPolicy(), executorService,
				this.mainMsgProcessor);
		serverProcess = new ServerProcess(config.getBindIp(),
				config.getServerName(), config.getPorts(), msgRecognizer,
				externalIoHandler, config.getIoProcessor(), config.getMisIps());

		// 初始化数据服务
		initDBService();

		// 管理服消息处理器
		manageMsgProcessor = new QueueMessageProcessor(
				"ProcessMessageFromManageServer");
		// 管理服相关
		ServerSessionManager serverSessionManager = new ServerSessionManager();
		ManageIoHandler manageIoHander = new ManageIoHandler(
				serverSessionManager, manageMsgProcessor);
		manageProcess = new ServerProcess(config.getTelnetBindIp(),
				config.getTelnetServerName(), config.getTelnetPort(),
				msgRecognizer, manageIoHander, config.getIoProcessor(),
				config.getMisIps());
		// 全局定时任务管理器
		globalHeartbeatObjectManager = ApplicationContext
				.getApplicationContext().getBean(
						GlobalHeartbeatObjectManager.class);
		// 初始化调度服务
		IScheduleService scheduleService = new ScheduleServiceImpl(
				this.mainMsgProcessor, ApplicationContext
						.getApplicationContext().getBean(
								SystemTimeService.class));
		ApplicationContext
				.getApplicationContext()
				.getDefaultListableBeanFactory()
				.registerResolvableDependency(IScheduleService.class,
						scheduleService);
	}

	private void start() throws Exception {
		logger.info("Begin to start Server Process...");
		mainMsgProcessor.start();
		serverProcess.start();
		manageMsgProcessor.start();
		manageProcess.start();
		dbExecutorService.start();
		globalHeartbeatObjectManager.init();
		GameServerAssist.start(dbServiceManager);
		// 开启汇报服务
		if (config.isUseLocalAuthorize()) {
			this.localConfig.startReportProcessor();
		}
		GameServerRuntime.setOpenOn();
		// 添加缓存数据库同步调度
		dbServiceManager.getDBMessageProcessor().addHeartBeatObject(
				dbServiceManager.getDBSynchronizer(),
				config.getDbSyncInterval());
		dbServiceManager.getDBSynchronizer().start();
		logger.info("Server Process started.");
		// 注册虚拟机关闭钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				shutdown();
			}
		});

	}

	/**
	 * 关机动作;
	 */
	protected void shutdown() {
		// 关闭汇报服务
		if (config.isUseLocalAuthorize()) {
			this.localConfig.stopReportProcessor();
		}
		// 关闭对外服务
		this.serverProcess.stop();
		this.manageProcess.stop();
		// 关闭游戏世界服务;
		ApplicationContext.getApplicationContext().getBean(GameWorld.class)
				.stop();
		// 停止数据服务;
		dbExecutorService.stop();
		dbServiceManager.getDBSynchronizer().synchronizedOnShutdown();
		dbServiceManager.getUpdateThread().stop();
		// 停止管理服务
		this.manageMsgProcessor.stop();
		this.manageProcess.stop();
		// 停止主线程;
		this.mainMsgProcessor.stop();
		this.serverProcess.stop();

	}

	/**
	 * 初始化数据服务
	 * 
	 * @throws IOException
	 * @throws ScriptException
	 */
	private void initDBService() {
		// DB操作开关
		if (config.isDbOperationOpen()) {
			DBOperationConfig.turnOn();
		}
		/** 数据消息处理器 */
		DBMessageHandler dbMessageHandler = new DBMessageHandler();
		// 数据服务管理器
		dbServiceManager = new DBServiceManager(dbMessageHandler,
				new DBConfiguration(config.getDbServiceType(), config
						.getDbConfigName(), config.getDataServiceProperties()),
				GameServerAssist.getUuidService(), this.localConfig);
		dbMessageHandler.setMainMessageProcessor(mainMsgProcessor);

		// 检查数据库版本
		dbServiceManager.getDBService().checkDbVersion(config.getDbVersion());

		ApplicationContext
				.getApplicationContext()
				.getDefaultListableBeanFactory()
				.registerResolvableDependency(IDBService.class,
						dbServiceManager.getDBService());
		// dbMessageHandler.setDBAgent(dbServiceManager.getDBAgent());
		dbExecutorService = dbServiceManager.getDBMessageProcessor();
		ApplicationContext.getApplicationContext().getBean(DataService.class)
				.setDbExecutor(dbExecutorService);

		// UUID服务初始化
		UUIDService uuidService = ApplicationContext.getApplicationContext()
				.getBean(UUIDService.class);
		uuidService.setRegionId(Integer.parseInt(config.getRegionId()));
		uuidService.setServerId(Integer.parseInt(config.getServerGroupId()));
		uuidService.setLineId(config.getServerIndex());
		uuidService.setUUIDTypes(UUIDType.values());
		// 此时要确保DBService已经初始化完成了;
		uuidService.setDBService(dbServiceManager.getDBService());
		uuidService.init();

	}

	public static void main(String[] args) {
		logger.info("Begin to start Game Server");

		try {
			ByteBuffer.setUseDirectBuffers(false);
			ByteBuffer.setAllocator(new SimpleByteBufferAllocator());
			GameServer server = new GameServer("game_server.cfg.js");
			server.init();
			server.start();
			logger.info("Game Server started");
		} catch (Exception e) {
			logger.error("Failed to start server", e);
			System.exit(1);
			return;
		}
	}
}
