package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.item.manager.HumanForgeManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetFreeEquipForgeTimesTask extends AbstractDailyTask {

	private HumanForgeManager manager;
	
	public ResetFreeEquipForgeTimesTask(HumanForgeManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_FREE_EQUIP_FORGE_TIMES.getIndex();
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
		manager.resetFreeResetTimes();
	}

}
