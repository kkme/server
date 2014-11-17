package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetAssistMeditationRemainTimeTask extends AbstractDailyTask {
	private Human human;
	public ResetAssistMeditationRemainTimeTask(Human human){
		this.human = human;		
	}
	@Override
	public int getTimeTaskType() {		
		return TimeTaskType.RESET_ASSIST_MEDITATION_REMAIN_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {		
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_ASSIST_MEDITATION_RESET_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getHumanPropertiesManager()
		.setLongPropertyValue(HumanLongProperty.LAST_ASSIST_MEDITATION_RESET_TIME,time);
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
		human.getHumanMeditationManager().resetRemainAssistMeditationTimes();
	}

}
