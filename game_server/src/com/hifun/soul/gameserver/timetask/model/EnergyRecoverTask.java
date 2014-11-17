package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractIntervalTask;

public class EnergyRecoverTask extends AbstractIntervalTask {
	private Human human;
	private long nowTime;
	public EnergyRecoverTask(Human human){
		this.human = human;
	}
	@Override
	public long getTimeSpan() {		
		return GameServerAssist.getGameConstants().getEnergyRecoverInterval()*TimeUtils.MIN;
	}
	@Override
	public long getLastRunTime(){
		return human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LAST_ENERGY_RECOVER_TIME);
	}
	@Override
	public void setLastRunTime(long time){
		human.getPropertyManager().setLongPropertyValue(HumanLongProperty.LAST_ENERGY_RECOVER_TIME, time);
	}
	
	@Override
	public boolean isTimeUp(long now){
		if(super.isTimeUp(now)){
			nowTime = now;
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if(isStop()){
			return;
		}
		if(human.getEnergy()+GameServerAssist.getGameConstants().getEnergyRecoverNum()>GameServerAssist.getGameConstants().getMaxEnergy()){
			return;
		}		
		int recoverNum = GameServerAssist.getGameConstants().getEnergyRecoverNum();
		if(getLastRunTime()!=0){
			recoverNum = (int) ((nowTime-getLastRunTime())/getTimeSpan())*recoverNum;
			if(recoverNum+human.getEnergy()>=GameServerAssist.getGameConstants().getMaxEnergy()){
				human.setEnergy(GameServerAssist.getGameConstants().getMaxEnergy(),EnergyLogReason.ENERGY_AUTO_RECOVER,"");
				return;
			}
		}		
		human.setEnergy(human.getEnergy()+recoverNum,EnergyLogReason.ENERGY_AUTO_RECOVER,"");
	}

}
