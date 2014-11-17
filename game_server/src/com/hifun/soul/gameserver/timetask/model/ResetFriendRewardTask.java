package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.friend.manager.HumanFriendManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetFriendRewardTask extends AbstractDailyTask {

	private HumanFriendManager manager;
	
	public ResetFriendRewardTask(HumanFriendManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_FRIEND_REWARD.getIndex();
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
		
		manager.resetFriendReward();
	}

}
