package com.hifun.soul.manageserver;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.orm.DBConfiguration;
import com.hifun.soul.core.orm.DBServiceFactory;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.manageserver.schedule.ScheduleService;
import com.hifun.soul.manageserver.schedule.message.CheckDbMessage;

@Scope("singleton")
@Component
public class DBServiceManager {
	private static final long CHECK_DB_PERIOD=1000*60*60; //1小时检查一次
	/** Manage-DB数据服务 */
	private IDBService manageDbService;
	/** Game-Db数据服务  */
	private IDBService gameDbService;
	
	private Map<Integer,DBConfiguration> gameDbConfig;
	private int currentServerId;	
	private CheckDbMessage checkDbMsg;
	private ScheduleService scheduleService;
	
	public DBServiceManager(){
		scheduleService = ApplicationContext.getApplicationContext().getBean(ScheduleService.class);
	}

	/**
	 * 使用DBConfiguration创建DBService的实例
	 * 
	 * @param manageDbConfig
	 */
	public void initDbService(DBConfiguration manageDbConfig, Map<Integer,DBConfiguration> gameDbConfig,int defualtServerId) {
		this.manageDbService = DBServiceFactory.createDBService(manageDbConfig);
		this.gameDbConfig = gameDbConfig;
		currentServerId = defualtServerId;
		gameDbService = DBServiceFactory.createDBService(gameDbConfig.get(currentServerId));		
		checkDbMsg = new CheckDbMessage();
		checkDbMsg.setGameDbService(gameDbService);
		checkDbMsg.setManageDbService(manageDbService);
		scheduleService.scheduleAtFixedRate(checkDbMsg, CHECK_DB_PERIOD, CHECK_DB_PERIOD);
	}

	public IDBService getManageDBService() {
		return manageDbService;
	}
	
	public void changeServer(Integer serverId) {
		if(serverId!=currentServerId){
			currentServerId = serverId;			
			gameDbService = DBServiceFactory.createDBService(gameDbConfig.get(serverId));
			checkDbMsg.setGameDbService(gameDbService);
		}
	}
	
	public IDBService getGameDbService(){
		return gameDbService;
	}		
	
}
