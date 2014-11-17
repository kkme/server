package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.legion.manager.HumanLegionManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 刷新角色军团任务
 * 
 * @author yandajun
 * 
 */
public class RefreshHumanLegionTask extends AbstractDailyTask {

	private HumanLegionManager manager;

	public RefreshHumanLegionTask(HumanLegionManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.REFRESH_LEGION_TASK.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastRefreshTaskTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastRefreshTaskTime(time);
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
		manager.refreshTaskTheme();
		manager.refreshTaskList(true);
	}

}
