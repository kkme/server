package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.GlobalKeyConstants;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;

/**
 * 战俘营重置数据任务
 * 
 * @author yandajun
 * 
 */
@Component
public class GlobalPrisonResetDataTask extends AbstractDailyTask {
	@Autowired
	private GlobalPrisonManager manager;
	@Autowired
	private GlobalTimeTaskManager globalTimeTaskManager;

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.PRISON_RESET_DATA.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return globalTimeTaskManager
				.getLastRunTime(GlobalKeyConstants.PRISON_DATA_RESET_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		globalTimeTaskManager.setLastRunTime(
				GlobalKeyConstants.PRISON_DATA_RESET_TIME, time);
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
		manager.resetPrisonerData();
	}

}
