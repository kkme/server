package com.hifun.soul.manageserver;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.LocalConstants;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.context.PackagePathType;
import com.hifun.soul.core.orm.DBConfiguration;
import com.hifun.soul.core.rpc.server.RpcServiceRouter;
import com.hifun.soul.core.server.CommonMessageRecognizer;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.core.server.ServerProcess;
import com.hifun.soul.manageserver.config.GameServerConfig;
import com.hifun.soul.manageserver.config.ManageServerConfig;
import com.hifun.soul.manageserver.config.ManageServerConfigManager;
import com.hifun.soul.manageserver.schedule.ScheduleService;
import com.hifun.soul.manageserver.service.AccountService;
import com.hifun.soul.manageserver.service.BulletinService;
import com.hifun.soul.manageserver.service.CharacterService;
import com.hifun.soul.manageserver.service.CharacterStatisticService;
import com.hifun.soul.manageserver.service.LogService;
import com.hifun.soul.manageserver.service.MailService;
import com.hifun.soul.manageserver.service.ManageLogService;
import com.hifun.soul.manageserver.service.MarketActService;
import com.hifun.soul.manageserver.service.QuestionService;
import com.hifun.soul.manageserver.service.RechargeService;
import com.hifun.soul.manageserver.service.RuntimeService;
import com.hifun.soul.manageserver.service.UserService;

/**
 * 管理服;
 * 
 * @author crazyjohn
 * 
 */
public class ManagementServer {
	/** 服务器网络IO事件处理 */
	private ServerProcess serverProcess;
	/** 消息队列 */
	private IMessageProcessor messageProcessor;
	/** 游戏服配置 */
	private ManageServerConfigManager configManager;
	/** 数据管理 */
	private DBServiceManager dbManager;
	/** 关联游戏服的session管理器 */
	private ServerSessionManager serverSessionManager;
	/** 定时任务管理器 */
	private ScheduleService scheduleService;
	private static Logger logger = Loggers.MANAGE_MAIN_LOGGER;

	public void init() throws ScriptException, IOException {
		// spring
		ApplicationContext.initAndScan(PackagePathType.PACKAGE_MANAGE_SERVER
				.getPackagePaths());
		configManager = ApplicationContext.getApplicationContext().getBean(
				ManageServerConfigManager.class);

		String manageServerConfigFile = "manage_server_config.js";
		String gameServerConfigFile = "game_server_config.xml";
		configManager.loadConfig(manageServerConfigFile, gameServerConfigFile);
		ManageServerConfig managerServerConfig = configManager
				.getManageServerConfig();
		messageProcessor = new QueueMessageProcessor(
				"ProcessMessageFromGameServer");
		serverProcess = new ServerProcess(managerServerConfig.getBindIp(),
				ManagementServer.class.getSimpleName(),
				managerServerConfig.getPorts(), new CommonMessageRecognizer(),
				new GmClientIoHandler(this.messageProcessor), "");
		serverSessionManager = ApplicationContext.getApplicationContext()
				.getBean(ServerSessionManager.class);
		serverSessionManager.init(configManager, this.messageProcessor);

		// 数据服务管理器
		initDBService(configManager);

		// 初始化TGW
		initTGW(managerServerConfig);
		
		//初始化定时任务
		scheduleService = ApplicationContext.getApplicationContext().getBean(ScheduleService.class);
		scheduleService.setMessageProcessor(messageProcessor);
	}

	/**
	 * 初始化TGW窜;
	 * 
	 * @param managerServerConfig
	 */
	private static void initTGW(ManageServerConfig managerServerConfig) {
		String realStr = MessageFormat.format(LocalConstants.TGW_TEMPLATE,
				managerServerConfig.getServerDomain(),
				managerServerConfig.getPorts());
		LocalConstants.TGW_LENGTH = (short) realStr.length();
	}

	private void initDBService(ManageServerConfigManager configManager) {
		ManageServerConfig managerServerConfig = configManager
				.getManageServerConfig();
		DBConfiguration managerDbConfig = new DBConfiguration(
				managerServerConfig.getDbServiceType(),
				managerServerConfig.getDbConfigName(),
				managerServerConfig.getDataServiceProperties());
		Map<Integer, DBConfiguration> gameServerDbConfigs = new HashMap<Integer, DBConfiguration>();
		for (Integer serverId : configManager.getGameServerConfigs().keySet()) {
			GameServerConfig gameServerConfig = configManager
					.getGameServerConfigs().get(serverId);
			DBConfiguration gameDbConfig = gameServerConfig.getGameDbConfig();
			gameServerDbConfigs.put(serverId, gameDbConfig);
		}
		
		/** 数据消息处理器 */
		dbManager = ApplicationContext.getApplicationContext().getBean(DBServiceManager.class);
		dbManager.initDbService(managerDbConfig, gameServerDbConfigs,
				configManager.getDefaultGameServerId());
		RpcServiceRouter router = ApplicationContext.getApplicationContext()
				.getBean(RpcServiceRouter.class);
		RpcCallbackManager callbackManager = ApplicationContext
				.getApplicationContext().getBean(RpcCallbackManager.class);
		ServerSessionManager sessionManager = ApplicationContext
				.getApplicationContext().getBean(ServerSessionManager.class);
		// 注册服务
		router.registerService(new UserService(dbManager.getManageDBService()));
		router.registerService(new AccountService(dbManager, sessionManager));
		router.registerService(ApplicationContext.getApplicationContext()
				.getBean(RuntimeService.class));
		router.registerService(new CharacterService(dbManager, sessionManager,
				callbackManager));
		router.registerService(new BulletinService(dbManager, sessionManager,
				callbackManager));
		router.registerService(new MailService(dbManager, sessionManager,
				callbackManager));
		router.registerService(new LogService());
		router.registerService(new ManageLogService(dbManager
				.getManageDBService()));
		router.registerService(new QuestionService(dbManager));
		router.registerService(new MarketActService(dbManager, sessionManager,
				callbackManager));
		router.registerService(new CharacterStatisticService(dbManager, sessionManager,
				callbackManager));
		router.registerService(new RechargeService(dbManager));
	}

	public void start() throws IOException {
		// 启动服务
		logger.info("Begin to start managementServer...");
		messageProcessor.start();
		serverProcess.start();
		serverSessionManager.openAllConnection();
		scheduleService.start();
		logger.info("ManagementServer started.");
	}

	public void stop() {
		// 停止服务
		logger.info("Begin to stop managementServer...");
		messageProcessor.stop();
		serverProcess.stop();
		scheduleService.stop();
		logger.info("ManagementServer stoped.");
	}

	public static void main(String[] args) {
		ManagementServer server = new ManagementServer();
		try {
			server.init();
			server.start();
		} catch (Exception e) {
			logger.error("Failed to start the managementServer", e);
			throw new RuntimeException("Failed to start the managementServer",
					e);
		}
	}

}
