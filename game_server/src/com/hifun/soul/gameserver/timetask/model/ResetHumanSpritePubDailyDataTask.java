package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.sprite.manager.HumanSpritePubManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 重置精灵酒馆每日数据任务
 * 
 * @author yandajun
 * 
 */
public class ResetHumanSpritePubDailyDataTask extends AbstractDailyTask {

	private HumanSpritePubManager manager;

	public ResetHumanSpritePubDailyDataTask(HumanSpritePubManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.REFRESH_LEGION_TASK.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastResetDailyDataTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastResetDailyDataTime(time);
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
		manager.resetPubDailyData();
	}
}
