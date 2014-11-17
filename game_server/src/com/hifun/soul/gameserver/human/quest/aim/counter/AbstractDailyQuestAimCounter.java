package com.hifun.soul.gameserver.human.quest.aim.counter;

import com.hifun.soul.gameserver.human.quest.IQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;

/**
 * 日常任务的计数器目标只有一个参数,所以另外抽象;
 * 
 * @author crazyjohn
 * 
 */
public abstract class AbstractDailyQuestAimCounter extends AbstractMainQuestAimCounter {

	public AbstractDailyQuestAimCounter(IQuestAimType type, AimInfo aim,
			int index) {
		super(type, aim, index);
	}

	@Override
	public boolean isFinished() {
		if (this.currentValue == this.aim.getParam1()) {
			return true;
		}
		return false;
	}
	

}
