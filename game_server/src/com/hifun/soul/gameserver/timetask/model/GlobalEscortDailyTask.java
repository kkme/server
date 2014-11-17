package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.GlobalKeyConstants;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;

/**
 * 全局押运每日定时任务
 * 
 * @author yandajun
 * 
 */
@Component
public class GlobalEscortDailyTask extends AbstractDailyTask {
	@Autowired
	private GlobalEscortManager globalEscortManager;
	@Autowired
	private GlobalTimeTaskManager globalTimeTaskManager;

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.GLOBAL_ESCORT_DAILY_TASK.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return globalTimeTaskManager
				.getLastRunTime(GlobalKeyConstants.ESCORT_DATA_RESET_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		globalTimeTaskManager.setLastRunTime(
				GlobalKeyConstants.ESCORT_DATA_RESET_TIME, time);
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
		globalEscortManager.resetDailyRobRankData();
	}

}
