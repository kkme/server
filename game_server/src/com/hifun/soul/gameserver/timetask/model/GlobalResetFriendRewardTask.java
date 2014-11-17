package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

@Component
public class GlobalResetFriendRewardTask extends AbstractDailyTask{
	@Autowired
	private FriendService friendService;
		
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_FRIEND_REWARD.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return friendService.getLastResetRewardTime();
	}

	@Override
	public void setLastRunTime(long time) {
		friendService.setLastResetRewardTime(time);
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
		friendService.resetFriendReward();
	}
}
