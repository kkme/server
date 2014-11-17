package com.hifun.soul.gameserver.crystalexchange.manager;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.crystalexchange.msg.GCShowCrystalExchangePanel;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 玩家魔晶兑换管理器
 * 
 * @author magicstone
 *
 */
public class HumanCrystalExchangeManager {
	private Human human;
	public HumanCrystalExchangeManager(Human human) {
		this.human = human;
	}
	
	public Human getHuman() {
		return human;
	}
	
	public int getUseTimes() {
		return human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.CRYSTAL_EXCHANGE_TIME);
	}
	
	public void setUseTimes(int useTimes) {
		human.getHumanPropertiesManager()
			.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.CRYSTAL_EXCHANGE_TIME, useTimes);
	}
	
	public void addUseTimes() {
		setUseTimes(getUseTimes() + 1);
	}
	
	public long getLastResetTime() {
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_CRYSTAL_EXCHANGE_RESET_TIME);
	}
	
	public void setLastResetTime(long lastResetTime) {
		human.getHumanPropertiesManager()
			.setLongPropertyValue(HumanLongProperty.LAST_CRYSTAL_EXCHANGE_RESET_TIME, lastResetTime);
	}
	
	/**
	 * 重置魔晶兑换次数
	 * 
	 */
	public void resetCrystalExchangeTimes() {
		setUseTimes(0);
	}

	public void onOpenClick() {
		GCShowCrystalExchangePanel gcMsg = new GCShowCrystalExchangePanel();
		int time = getUseTimes();
		int vipLevel = human.getVipLevel();
		int totalTime = GameServerAssist.getCrystalExchangeTemplateManager().getTotalCrystalExchangeTime(vipLevel);
		int remainTime = totalTime - time>0 ? totalTime - time : 0;
		gcMsg.setUseTimes(remainTime);
		gcMsg.setTotalTimes(totalTime);
		gcMsg.setCrystal(GameServerAssist.getCrystalExchangeTemplateManager().getCrystalExchangeConsume(time+1,vipLevel));
		gcMsg.setCoin(GameServerAssist.getCrystalExchangeTemplateManager().getCrystalExchangeReward(vipLevel,human.getLevel()));
		human.sendMessage(gcMsg);
	}

	public int getLeftExchangeTimes() {
		int time = getUseTimes();
		int vipLevel = human.getVipLevel();
		int totalTime = GameServerAssist.getCrystalExchangeTemplateManager().getTotalCrystalExchangeTime(vipLevel);
		int remainTimes = totalTime - time  ;		
		return remainTimes > 0 ? remainTimes : 0;
	}
}
