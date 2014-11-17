package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.crystalexchange.manager.HumanCrystalExchangeManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetCrystalExchangeTimeTask extends AbstractDailyTask {

	private HumanCrystalExchangeManager manager;
	
	public ResetCrystalExchangeTimeTask(HumanCrystalExchangeManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_CRYSTAL_EXCHANGE_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastResetTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastResetTime(time);
	}

	@Override
	public boolean needRunMissing() {
		return true;
	}
	
	@Override
	public void run() {
		if(isStop()){
			return;
		}
		
		manager.resetCrystalExchangeTimes();
	}

}
