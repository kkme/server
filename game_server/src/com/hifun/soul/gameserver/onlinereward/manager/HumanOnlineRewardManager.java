package com.hifun.soul.gameserver.onlinereward.manager;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.onlinereward.msg.GCShowOnlineReward;
import com.hifun.soul.gameserver.onlinereward.template.OnlineRewardTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

public class HumanOnlineRewardManager implements ILoginManager{
	private Logger logger = Loggers.ONLINEREWARD_LOGGER;
	private Human human;
	public HumanOnlineRewardManager(Human human) {
		this.human = human;
		
		this.human.registerLoginManager(this);
	}
	
	public int getTimes() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.ONLINE_REWARD_TIME);
	}
	
	public void setTimes(int times) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.ONLINE_REWARD_TIME, times);
	}
	
	public void setLastGetTime(long lastGetTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.ONLINT_REWARD_GET_TIME, lastGetTime);
	}
	
	public long getLastGetTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(HumanLongProperty.ONLINT_REWARD_GET_TIME);
	}
	
	public boolean canGet() {
		OnlineRewardTemplate template = GameServerAssist.getOnlineRewardTemplateManager().getOnlineRewardTemplate(getTimes()+1, human.getOccupation().getIndex());
		if(template == null){
			logger.error("no OnlineRewardTemplate !");
			return false;
		}
		if(GameServerAssist.getSystemTimeService().now() - getLastGetTime() >= template.getCd()*TimeUtils.MIN){
			return true;
		}
		
		return false;
	}

	@Override
	public void onLogin() {
		OnlineRewardTemplate template = GameServerAssist.getOnlineRewardTemplateManager().getOnlineRewardTemplate(getTimes()+1, human.getOccupation().getIndex());
		if(template == null){
			return;
		}
		long passTime = human.getLastLogoutTime()-getLastGetTime();
		passTime = passTime > 0 ? passTime : 0;
		int seconds = (int) ((template.getCd()*TimeUtils.MIN-passTime)/TimeUtils.SECOND);
		if(seconds < 0){
			seconds = 0;
		}		
		GCShowOnlineReward gcMsg = new GCShowOnlineReward();
		gcMsg.setSeconds(seconds);
		SimpleCommonItem commonItem = CommonItemBuilder.genSimpleCommonItem(template.getItemId());
		if(commonItem != null){
			SimpleCommonItem[] commonItems = new SimpleCommonItem[1];
			commonItems[0] = commonItem;
			gcMsg.setReward(commonItems);
		}
		else{
			gcMsg.setReward(new SimpleCommonItem[0]);
		}
		
		human.sendMessage(gcMsg);
	}
}
