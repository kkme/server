package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.template.BuyEnergyCostTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 购买体力重置任务
 * 
 * @author magicstone
 *
 */
public class ResetBuyEnergyTimesDailyTask extends AbstractDailyTask{

	private Human human; 
	
	public ResetBuyEnergyTimesDailyTask(Human human){
		this.human = human;
	}
	@Override
	public int getTimeTaskType() {		
		return TimeTaskType.RESET_BUY_ENERGY_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {		
		return human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LAST_RESET_BUY_ENERGY_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getPropertyManager().setLongPropertyValue(HumanLongProperty.LAST_RESET_BUY_ENERGY_TIME,time);
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
		HumanPropertyManager propertyManager = human.getPropertyManager();
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.BUY_ENERGY_REMAIN_TIMES, GameServerAssist.getVipPrivilegeTemplateManager().getMaxBuyEnergyTimes(human.getVipLevel()));
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.BUY_ENERGY_TIMES,0);
		BuyEnergyCostTemplate template = GameServerAssist.getEnergyTemplateManager().getBuyEnergyCostTemplate(1);
		//下次购买体力花费			
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.BUY_ENERGY_CRYSTAL_COST_NUM,template.getCrystalCost());
		//下次购买体力获得的点数
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.BUY_ENERGY_ADD_NUM,template.getEnergyNum());
	}

}
