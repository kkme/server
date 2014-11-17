package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.loginreward.manager.HumanLoginRewardManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetLoginRewardTask extends AbstractDailyTask {

	private HumanLoginRewardManager manager;
	
	public ResetLoginRewardTask(HumanLoginRewardManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_LOGIN_REWARD.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastDaysUpdateTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastDaysUpdateTime(time);
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
		
		manager.updateLoginReward();
	}

}
