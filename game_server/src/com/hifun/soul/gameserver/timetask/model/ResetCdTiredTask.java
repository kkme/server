package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetCdTiredTask extends AbstractDailyTask {

	private HumanCdManager manager;
	
	public ResetCdTiredTask(HumanCdManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_CD_TIRED.getIndex();
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
		
		manager.resetCdTired();
	}

}
