package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.abattoir.manager.HumanAbattoirManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 角斗场数据重置任务
 * 
 * @author 闫大俊
 * 
 */
public class ResetAbattoirDataTask extends AbstractDailyTask {

	private HumanAbattoirManager manager;

	public ResetAbattoirDataTask(HumanAbattoirManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_HUMAN_ABATTOIR_DATA.getIndex();
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
		manager.resetHumanAbattoirData();
	}

}
