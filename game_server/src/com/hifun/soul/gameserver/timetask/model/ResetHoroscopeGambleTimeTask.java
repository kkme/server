package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetHoroscopeGambleTimeTask extends AbstractDailyTask {

	private HumanHoroscopeManager manager;
	
	public ResetHoroscopeGambleTimeTask(HumanHoroscopeManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_HOROSCOPE_GAMBLE_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastResetGambleTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastResetGambleTime(time);
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
		
		manager.setHoroscopeGambleTime(SharedConstants.HOROSCOPE_GAMBLE_TIME);
	}

}
