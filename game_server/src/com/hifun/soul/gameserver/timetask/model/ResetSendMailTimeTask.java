package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 重置发送邮件的次数
 * @author magicstone
 *
 */
public class ResetSendMailTimeTask extends AbstractDailyTask {

	private Human human;
	public ResetSendMailTimeTask(Human human){
		this.human = human;
	}
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_SEND_MAIL_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(HumanLongProperty.LAST_RESET_SEND_MAIL_NUM_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.LAST_RESET_SEND_MAIL_NUM_TIME,time);
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
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.SEND_MAIL_TIMES,0);
	}

}
