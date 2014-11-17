package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.shop.manager.HumanSpecialShopManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetSpecialShopRefreshTimeTask extends AbstractDailyTask {

	private HumanSpecialShopManager manager;
	
	public ResetSpecialShopRefreshTimeTask(HumanSpecialShopManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_SPECIAL_SHOP_REFRESH_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastResetTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastResetTime(time);
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
		
		manager.resetSpecialShopBuyTime();
	}

}
