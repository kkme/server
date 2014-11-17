package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.bloodtemple.manager.HumanBloodTempleManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 角斗场数据重置任务
 * 
 * @author yandajun
 * 
 */
public class ResetBloodTempleDataTask extends AbstractDailyTask {

	private HumanBloodTempleManager manager;

	public ResetBloodTempleDataTask(HumanBloodTempleManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_HUMAN_BLOOD_TEMPLE_DATA.getIndex();
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
		manager.resetHumanBloodTempleData();
	}

}
