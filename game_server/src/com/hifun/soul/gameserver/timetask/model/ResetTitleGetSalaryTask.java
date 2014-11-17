package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;
import com.hifun.soul.gameserver.title.manager.HumanTitleManager;

/**
 * 重置军衔每日俸禄领取状态定时任务
 * 
 * @author yandajun
 * 
 */
public class ResetTitleGetSalaryTask extends AbstractDailyTask {
	private HumanTitleManager manager;

	public ResetTitleGetSalaryTask(HumanTitleManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_TITLE_GET_SALARY.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastUpdateGetSalaryTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastUpdateGetSalaryTime(time);
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
		manager.updateIsGotSalary();
	}

}
