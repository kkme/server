package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.GlobalKeyConstants;
import com.hifun.soul.gameserver.mars.manager.GlobalMarsManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;

/**
 * 军衔排行榜定时刷新任务
 * 
 * @author yandajun
 * 
 */
@Component
public class ResetGlobalMarsDataDailyTask extends AbstractDailyTask {
	@Autowired
	private GlobalMarsManager globalMarsManager;
	@Autowired
	private GlobalTimeTaskManager globalTimeTaskManager;

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_GLOBAL_MARS_DAILY_DATA.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return globalTimeTaskManager
				.getLastRunTime(GlobalKeyConstants.MARS_DATA_RESET_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		globalTimeTaskManager.setLastRunTime(
				GlobalKeyConstants.MARS_DATA_RESET_TIME, time);

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
		globalMarsManager.resetDailyData();
	}

}
