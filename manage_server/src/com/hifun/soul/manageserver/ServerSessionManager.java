package com.hifun.soul.manageserver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.client.NIOClient;
import com.hifun.soul.core.client.NIOClient.ConnectionCallback;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.server.CommonMessageRecognizer;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.session.ServerSession;
import com.hifun.soul.manageserver.config.GameServerConfig;
import com.hifun.soul.manageserver.config.ManageServerConfigManager;

/**
 * 服务器间的回话管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class ServerSessionManager {
	private static Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	private ServerSession gameServer;
	/** 管理服到游戏服务的连接 */
	private NIOClient gameServerConnection;
	private Map<Integer, NIOClient> gameServerConnections;
	private Map<Integer, ServerSession> gameServers = new HashMap<Integer,ServerSession>();
	private int currenctGameServerId;

	public ServerSession getGameServer() {
		if(!gameServers.containsKey(currenctGameServerId)){
			if(gameServerConnection!=null && !gameServerConnection.isConnected()){
				gameServerConnection.open();
			}
			return null;
		}
		this.gameServer = gameServers.get(currenctGameServerId);		
		return gameServer;
	}
	
	public ServerSession getGameServer(int serverId){
		return gameServers.get(serverId);
	}

	public void setGameServer(int serverId, ServerSession gameServer) {
		gameServers.put(serverId, gameServer);
	}
	
	public void removeGameServer(int serverId) {
		if(gameServers.containsKey(serverId)){
			gameServers.remove(serverId);
		}		
	}

	/**
	 * 初始化与游戏服的连接
	 * 
	 * @param configManager
	 */
	public void init(ManageServerConfigManager configManager, IMessageProcessor messageProcessor) {
		gameServerConnections = new HashMap<Integer, NIOClient>();
		for (Integer serverId : configManager.getGameServerConfigs().keySet()) {
			GameServerConfig serverConfig = configManager.getGameServerConfigs().get(serverId);
			NIOClient connection = new NIOClient("GameServer", serverConfig.getHost(), serverConfig.getPort(),
					Executors.newSingleThreadExecutor(), messageProcessor, new CommonMessageRecognizer(),
					new GameIoHandler(this, messageProcessor, serverConfig.getServerId()), new ConnectionCallback() {
						@Override
						public void onOpen(NIOClient client, IMessage msg) {
							logger.info("Succeed connected to gameServer.");
						}

						@Override
						public void onClose(NIOClient client, IMessage msg) {
							logger.error("Connected to gameServer failed.");

						}
					});
			gameServerConnections.put(serverId, connection);
		}
		currenctGameServerId = configManager.getDefaultGameServerId();
		gameServerConnection = gameServerConnections.get(currenctGameServerId);
	}

	/**
	 * 打开所有的游戏服务器连接
	 */
	public void openAllConnection() {
		for(NIOClient conn : gameServerConnections.values()){
			conn.open();
		}
	}
	
	public boolean openConnection(){
		return openConnection(currenctGameServerId);
	}

	/**
	 * 打开指定server的连接
	 * 
	 * @param serverId
	 */
	public boolean openConnection(int serverId) {
		if (gameServerConnections.containsKey(serverId)) {
			NIOClient connection = gameServerConnections.get(serverId);
			if (!connection.isConnected()) {
				return connection.open();
			}
			return true;
		}
		return false;
	}

	/**
	 * 关闭指定server的连接
	 * 
	 * @param serverId
	 */
	public void closeConnection(int serverId) {
		if (gameServerConnections.containsKey(serverId)) {
			gameServerConnections.get(serverId).close();
		}
	}

	public boolean changeCurrentGameServer(int serverId) {
		if (gameServerConnections.containsKey(serverId)) {
			if (serverId == currenctGameServerId) {
				if (!gameServerConnection.isConnected()) {
					return gameServerConnection.open();
				}				
			} else {
				currenctGameServerId = serverId;
				gameServerConnection = gameServerConnections.get(serverId);
				if (!gameServerConnection.isConnected()) {
					return gameServerConnection.open();
				}				
			}
		}
		return true;
	}

	/**
	 * 获取当前连接的游戏服务器id
	 * 
	 * @return
	 */
	public int getCurrenctGameServerId() {
		return currenctGameServerId;
	}
	
}
