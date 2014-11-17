package com.hifun.soul.manageserver.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.script.ScriptException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.config.ConfigUtil;
import com.hifun.soul.core.orm.DBConfiguration;
import com.hifun.soul.manageserver.Loggers;

/**
 * 游戏服的配置管理器
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class ManageServerConfigManager {
	private ManageServerConfig manageServerConfig;
	private Map<Integer,GameServerConfig> gameServerConfigs;
	/** 默认连接的游戏服id */
	private int defaultGameServerId;
	private static Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	public void loadConfig(String manageServerConfigPath,String gameServerConfigPath) throws ScriptException, IOException{
		manageServerConfig = new ManageServerConfig();
		
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		ConfigUtil.loadJsConfig(manageServerConfig, manageServerConfigPath);
		URL url = classLoader.getResource(gameServerConfigPath);
		loadServerConfig(url.openStream());
	}
	
	private void loadServerConfig(InputStream configPath){
		logger.info("Load serverConfigs: " + configPath);
		gameServerConfigs = new HashMap<Integer,GameServerConfig>();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(configPath);
			Element root = document.getRootElement();
			defaultGameServerId = Integer.parseInt(root.elementTextTrim("defualtGameServer"));
			for(Object child : root.elements("gameServer")){
				Element element = (Element)child;
				GameServerConfig config = new GameServerConfig();
				config.setServerId(Integer.parseInt(element.elementTextTrim("serverId")));
				config.setServerName(element.elementTextTrim("serverName"));
				config.setHost(element.element("host").getText());
				config.setPort(Integer.parseInt(element.element("port").getTextTrim()));				
				Element dbElement = element.element("dbConfig");
				String dbServiceType = dbElement.element("dbServiceType").getTextTrim();
				String dbConfigFileName=dbElement.elementTextTrim("dbConfigName");
				Properties dbProperties = new Properties();
				dbProperties.put("hibernate.connection.url", dbElement.elementTextTrim("url"));
				dbProperties.put("hibernate.connection.username", dbElement.elementTextTrim("userName"));
				dbProperties.put("hibernate.connection.password", dbElement.elementTextTrim("password"));
				DBConfiguration dbConfig = new DBConfiguration(dbServiceType, dbConfigFileName, dbProperties);
				config.setGameDbConfig(dbConfig);
				gameServerConfigs.put(config.getServerId(),config);
			}
		} catch (DocumentException e) {			
			e.printStackTrace();
		}
	}

	public ManageServerConfig getManageServerConfig() {
		return manageServerConfig;
	}

	public void setManageServerConfig(ManageServerConfig manageServerConfig) {
		this.manageServerConfig = manageServerConfig;
	}

	public Map<Integer, GameServerConfig> getGameServerConfigs() {
		return gameServerConfigs;
	}

	public void setGameServerConfigs(Map<Integer, GameServerConfig> gameServerConfigs) {
		this.gameServerConfigs = gameServerConfigs;
	}

	public int getDefaultGameServerId() {
		return defaultGameServerId;
	}

	public void setDefaultGameServerId(int defaultGameServerId) {
		this.defaultGameServerId = defaultGameServerId;
	}
	
}

