package com.hifun.soul.manageweb;

import java.io.IOException;
import java.net.URL;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.hifun.soul.core.config.ConfigUtil;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.context.PackagePathType;
import com.hifun.soul.manageweb.config.ManageServerConfigManager;
import com.hifun.soul.manageweb.config.ManageWebConfig;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.service.ServiceManager;
import com.hifun.soul.manageweb.template.manager.TemplateManager;

public class Managers {
	private static final String MANAGE_WEB_CONFIG_PATH="manage_web_config.js";
	private static final String MANAGE_SERVER_CONFIG_PATH="manage_server_config.xml";
	/** 服务管理器 */
	private static ServiceManager serviceManager;
	private static Logger logger = Loggers.MAIN_LOGGER;

	public static void init() {
		ManageWebConfig config;
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classLoader.getResource(MANAGE_WEB_CONFIG_PATH);
		config = ConfigUtil.buildConfig(ManageWebConfig.class, url);
		ApplicationContext.initAndScan(PackagePathType.PACKAGE_MANAGE_WEB.getPackagePaths());
		ManageServerConfigManager serverConfigManager = ApplicationContext.getApplicationContext().getBean(ManageServerConfigManager.class);
		try {
			serverConfigManager.loadConfig(MANAGE_SERVER_CONFIG_PATH);
		} catch (ScriptException e) {			
			e.printStackTrace();
			logger.error("init manage server config failed!");
			return;
		} catch (IOException e) {			
			e.printStackTrace();
			logger.error("init manage server config failed!");
			return;
		}
		TemplateManager.initTemplateService(config);
		// 初始化服务;
		serviceManager = ApplicationContext.getApplicationContext().getBean(ServiceManager.class);
		serviceManager.init(serverConfigManager.getCurrentManagerServerConfig());
		// 启动gm操作日志
		ManageLogger.start();
		//webRpc连接检查任务
		WebRpcCheckTask webRpcCheckTask = new WebRpcCheckTask();
		webRpcCheckTask.start(serviceManager);
	}

	public static ServiceManager getServiceManager() {
		return serviceManager;
	}

	
	
}
