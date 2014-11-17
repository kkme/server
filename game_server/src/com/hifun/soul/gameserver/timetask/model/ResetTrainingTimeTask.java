package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;
import com.hifun.soul.gameserver.training.manager.HumanTrainingManager;

public class ResetTrainingTimeTask extends AbstractDailyTask {

	private HumanTrainingManager humanTrainingManager;
	public ResetTrainingTimeTask(HumanTrainingManager humanTrainingManager){
		this.humanTrainingManager = humanTrainingManager;
	}
	@Override
	public int getTimeTaskType() {		
		return TimeTaskType.RESET_TRAINING_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return humanTrainingManager.getLastResetTime();
	}

	@Override
	public void setLastRunTime(long time) {
		humanTrainingManager.setLastResetTime(time);
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
		humanTrainingManager.resetTrainingData();
	}

}
