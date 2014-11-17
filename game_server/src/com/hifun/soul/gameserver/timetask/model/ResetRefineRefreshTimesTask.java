package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.refine.manager.HumanRefineManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetRefineRefreshTimesTask extends AbstractDailyTask {

	private HumanRefineManager manager;
	
	public ResetRefineRefreshTimesTask(HumanRefineManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_REFINE_REFRESH_TIMES.getIndex();
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
		manager.resetRefineTimesAndRefineStages();
	}

}
