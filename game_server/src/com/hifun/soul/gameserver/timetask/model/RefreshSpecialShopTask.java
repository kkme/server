package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.shop.SpecialShopRefreshType;
import com.hifun.soul.gameserver.shop.manager.HumanSpecialShopManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class RefreshSpecialShopTask extends AbstractDailyTask {

	private HumanSpecialShopManager manager;
	
	public RefreshSpecialShopTask(HumanSpecialShopManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.REFRESH_SPECIAL_SHOP.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastRefreshTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastRefreshTime(time);
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
		
		manager.refreshSpecialShop(SpecialShopRefreshType.COIN);
	}

}
