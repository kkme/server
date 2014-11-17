package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.activity.GlobalActivityManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 活动定时任务
 * <pre>功能：1、刷新有效活动列表</pre>
 * @author magicstone
 *
 */
@Component
public class ActivityDailyTask extends AbstractDailyTask {
	@Autowired
	private GlobalActivityManager activityManager;
	private long nowTime;
	private long lastRunTime=0L;
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_ACTIVITY_LIST.getIndex();
	}

	@Override
	public long getLastRunTime() {		
		return this.lastRunTime;
	}

	@Override
	public void setLastRunTime(long time) {
			this.lastRunTime = time;
	}

	@Override
	public boolean needRunMissing() {		
		return true;
	}
	
	@Override
	public boolean isTimeUp(long now){
		if(super.isTimeUp(now)){
			nowTime = now;
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if(isStop()){
			return;
		}
		activityManager.refreshALiveActivity(nowTime);
	}

	

}
