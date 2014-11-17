package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.GlobalKeyConstants;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;

/**
 * 重置全局军团每日数据
 * 
 * @author yandajun
 * 
 */
@Component
public class GlobalResetLegionDailyDataTask extends AbstractDailyTask {
	@Autowired
	private GlobalLegionManager globalLegionManager;
	@Autowired
	private GlobalTimeTaskManager globalTimeTaskManager;

	@Override
	public void run() {
		globalLegionManager.resetLegionDailyData();
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.GLOBAL_LEGION_DAILY_TASK.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return globalTimeTaskManager
				.getLastRunTime(GlobalKeyConstants.LEGION_DATA_RESET_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		globalTimeTaskManager.setLastRunTime(
				GlobalKeyConstants.LEGION_DATA_RESET_TIME, time);
	}

	@Override
	public boolean needRunMissing() {
		return false;
	}

}
