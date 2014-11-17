package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.antiindulge.service.ILocalService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.timetask.AbstractIntervalTask;

public class UpdateRevenueRateTask extends AbstractIntervalTask {
	private Human human;
	private ILocalService localService;
	
	public UpdateRevenueRateTask(Human human,ILocalService localService){
		this.human = human;
		this.localService = localService;
	}

	@Override
	public void run() {
		if(!GameServerAssist.getGameConstants().getAntiIndulgeSwitch()){
			return;
		}
		if(isStop()){
			return;
		}
		localService.updateRevenueRate(human);		
	}

	@Override
	public long getTimeSpan() {		
		return GameServerAssist.getGameConstants().getUpdateRevenueRateTimeSpan();
	}

	
	
}
