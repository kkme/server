package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 玩家每日提交问题反馈次数重设
 * 
 * @author magicstone
 *
 */
public class ResetSubmitGmQuestionTimeTask extends AbstractDailyTask {
	private Human human;
	
	public ResetSubmitGmQuestionTimeTask(Human human){
		this.human = human;
	}
	@Override
	public int getTimeTaskType() {		
		return TimeTaskType.RESET_SUBMIT_GM_QUESTION_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LAST_RESET_SUBMIT_GM_QUESTION_NUM_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getPropertyManager().setLongPropertyValue(HumanLongProperty.LAST_RESET_SUBMIT_GM_QUESTION_NUM_TIME, time);
	}

	@Override
	public boolean needRunMissing() {
		return true;
	}

	@Override
	public void run() {
		if(!isStop()){
			return;
		}
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.SUBMIT_GM_QUESTION_TIMES, 0);
	}

}
