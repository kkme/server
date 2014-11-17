package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.levy.HumanLevyManager;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 税收重置任务
 *
 * @author magicstone
 *
 */
public class ResetLevyTask extends AbstractDailyTask {

	private HumanLevyManager manager;
	
	public ResetLevyTask(HumanLevyManager manager){
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_LEVY.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getOwner().getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_LEVY_RESET_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		manager.getOwner().getHumanPropertiesManager()
		.setLongPropertyValue(HumanLongProperty.LAST_LEVY_RESET_TIME, time);		
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
		manager.resetData();
	}


}
