package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetHumanMarsDataDailyTask extends AbstractDailyTask {
	private Human human;

	public ResetHumanMarsDataDailyTask(Human human) {
		this.human = human;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_HUMAN_MARS_DAILY_DATA.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return human.getPropertyManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_MARS_DAILY_DATA_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getPropertyManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_MARS_DAILY_DATA_TIME, time);
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
		human.getHumanMarsManager().resetMarsDailyData();
	}

}
