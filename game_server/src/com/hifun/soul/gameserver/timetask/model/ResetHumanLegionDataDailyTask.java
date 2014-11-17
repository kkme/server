package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.legion.manager.HumanLegionManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 重置角色军团每日数据任务
 * 
 * @author yandajun
 * 
 */
public class ResetHumanLegionDataDailyTask extends AbstractDailyTask {

	private HumanLegionManager manager;

	public ResetHumanLegionDataDailyTask(HumanLegionManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_LEGION_DAILY_DATA.getIndex();
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
		manager.resetDailyData();
	}

}
