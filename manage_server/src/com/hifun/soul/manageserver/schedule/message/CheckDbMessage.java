package com.hifun.soul.manageserver.schedule.message;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.manageserver.db.PoolManager;

public class CheckDbMessage extends SceneScheduleMessage {
	private IDBService gameDbService;
	private IDBService manageDbService;
	
	public void setGameDbService(IDBService dbService){			
		gameDbService = dbService;
	}
	
	public void setManageDbService(IDBService dbService){			
		manageDbService = dbService;
	}	

	@Override
	public void execute() {
		if(gameDbService != null){
			gameDbService.check();
		}
		if(manageDbService != null){
			manageDbService.check();
		}
		PoolManager.getInstance().checkConnection();
	}

}
