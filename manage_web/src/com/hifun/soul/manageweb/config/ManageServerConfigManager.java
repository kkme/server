package com.hifun.soul.manageweb.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.hifun.soul.manageweb.Loggers;

/**
 * 游戏服的配置管理器
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class ManageServerConfigManager {
	private Map<Integer, ManageServerConfig> manageServerConfigs;
	/** 默认连接的游戏服id */
	private int currentServerId;
	private static Logger logger = Loggers.MAIN_LOGGER;

	public void loadConfig(String manageServerConfigPath) throws ScriptException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(manageServerConfigPath);
		loadServerConfig(url.openStream());
	}

	private void loadServerConfig(InputStream configPath) {
		logger.info("Load serverConfigs: " + configPath);
		manageServerConfigs = new HashMap<Integer, ManageServerConfig>();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(configPath);
			Element root = document.getRootElement();
			currentServerId = Integer.parseInt(root.elementTextTrim("defualtManageServer"));
			for (Object child : root.elements("manageServer")) {
				Element element = (Element) child;
				ManageServerConfig config = new ManageServerConfig();
				config.setServerId(Integer.parseInt(element.elementTextTrim("serverId")));
				config.setServerName(element.elementTextTrim("serverName"));
				config.setHost(element.element("host").getText());
				config.setPort(Integer.parseInt(element.element("port").getTextTrim()));
				manageServerConfigs.put(config.getServerId(), config);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCurrentServerConfig(int serverId){
		ManageServerConfig config = manageServerConfigs.get(serverId);
		if(config != null){
			this.currentServerId = serverId;
		}
	}

	public ManageServerConfig getManageServerConfig(int serverId) {
		return manageServerConfigs.get(serverId);
	}
	
	public ManageServerConfig getCurrentManagerServerConfig(){
		return manageServerConfigs.get(currentServerId);
	}

	public int getCurrentManagerServerId() {
		return currentServerId;
	}

	public Collection<ManageServerConfig> getAllManageServers() {		
		return manageServerConfigs.values();
	}

}
