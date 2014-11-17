package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 重设每日问答数据
 * 
 * @author magicstone
 * 
 */
public class ResetAnswerQuestionDailyTask extends AbstractDailyTask {

	private AnswerQuestionManager manager;

	public ResetAnswerQuestionDailyTask(AnswerQuestionManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_DAILY_ANSWER_QUESTION.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastDailyResetTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastDailyResetTime(time);

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
		long beginFormToday = TimeUtils.getBeginOfDay(getRunTime());
		int needResetTime = 0;		
		if (getLastRunTime() > 0) {
			int daySpend = 0;
			long resetTimeSpend = beginFormToday - TimeUtils.getBeginOfDay(this.getLastRunTime());
			daySpend = (int) (resetTimeSpend / TimeUtils.DAY) - 1;
			if(daySpend>0){
				needResetTime = daySpend * runTimes.length;
			}
		}		
		
		for (long runTime : runTimes) {
			if (getRunTime() >= runTime+beginFormToday) {
				needResetTime++;
				continue;
			}
			break;
		}
		manager.resetDailyData(needResetTime);
	}

}
