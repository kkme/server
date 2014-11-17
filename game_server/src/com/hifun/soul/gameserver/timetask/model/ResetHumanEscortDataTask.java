package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.escort.manager.HumanEscortManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 重置角色每日押运数据
 * 
 * @author yandajun
 * 
 */
public class ResetHumanEscortDataTask extends AbstractDailyTask {

	private HumanEscortManager manager;

	public ResetHumanEscortDataTask(HumanEscortManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_HUMAN_ESCORT_DAILY_TASK.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastResetDataTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastResetDataTime(time);
	}

	@Override
	public boolean needRunMissing() {
		return true;
	}

	@Override
	public void run() {
		if (isStop()) {
			return;
		}
		manager.resetEscortData();
	}

}
