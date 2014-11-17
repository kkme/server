package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.quest.manager.HumanQuestManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetDailyQuestTask extends AbstractDailyTask {

	private HumanQuestManager manager;
	
	public ResetDailyQuestTask(HumanQuestManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_DAILY_QUEST.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastResetDailyQuestTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastResetDailyQuestTime(time);
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
		
		manager.resetDailyQuests();
	}

}
