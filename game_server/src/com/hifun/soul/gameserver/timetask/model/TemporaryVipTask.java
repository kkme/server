package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

public class TemporaryVipTask implements IHeartBeatTask {
		
	private Human human; 
	
	public TemporaryVipTask(Human human){
		this.human = human;
	}
	@Override
	public void run() {
		human.getHumanVipManager().setVipLevelTemporary(0);	
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isTimeUp(long now) {		
		if(human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.VIP_LEVEL_TEMPORARY)>0 && human.getHumanCdManager().canAddCd(CdType.VIP_TEMP,0L)){
			return true;
		}
		return false;
	}

	@Override
	public void nextRound() {
		
	}

	@Override
	public long getRunTime() {		
		return 0;
	}

}
