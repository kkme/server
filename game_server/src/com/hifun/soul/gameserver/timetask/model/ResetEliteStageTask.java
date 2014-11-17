package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetEliteStageTask extends AbstractDailyTask {

	private Human human;
	public ResetEliteStageTask(Human human){
		this.human = human;
	}
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_ELITE_STAGE.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LAST_RESET_ELITE_STAGE_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getPropertyManager().setLongPropertyValue(HumanLongProperty.LAST_RESET_ELITE_STAGE_TIME,time);
	}

	@Override
	public boolean needRunMissing() {
		return true;
	}

	@Override
	public void run() {
		if(isStop()){
			return;
		}
		human.getHumanEliteStageManager().resetDailyData();
	}

}
