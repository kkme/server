package com.hifun.soul.gameserver.turntable.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.util.MathUtils;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.turntable.MinAndMaxValue;
import com.hifun.soul.gameserver.turntable.msg.GCShowTurntablePanel;
import com.hifun.soul.gameserver.turntable.template.ItemRateData;
import com.hifun.soul.gameserver.turntable.template.TurntableTemplate;

public class HumanTurntableManager {
	private Human human;
	public HumanTurntableManager(Human human) {
		this.human = human;
	}
	
	public Human getHuman() {
		return human;
	}
	
	/**
	 * 获取免费抽奖次数
	 * 
	 * @return
	 */
	public int getTurntableTime() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.TURNTABLE_TIME);
	}
	
	/**
	 * 设置免费抽奖次数
	 * 
	 * @param times
	 */
	public void setTurntableTime(int times) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.TURNTABLE_TIME, times);
	}
	
	/**
	 * 获取花费魔晶抽奖次数
	 * 
	 * @return
	 */
	public int getTurntableUseCrystalTime() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.TURNTABLE_USE_CRYSTAL_TIME);
	}
	
	public int getTurntableCrystalCost(){
		int times = getTurntableUseCrystalTime();
		return GameServerAssist.getTurntableTemplateManager().getTurntableCrystalCost(times+1);
	}
	
	/**
	 * 设置花费魔晶抽奖次数
	 * 
	 * @param times
	 */
	public void setTurntableUseCrystalTime(int times) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.TURNTABLE_USE_CRYSTAL_TIME, times);
	}
	
	public long getLastResetTime() {
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_TURNTABLE_RESET_TIME);
	}

	public void setLastResetTime(long lastResetTime) {
		human.getHumanPropertiesManager()
			.setLongPropertyValue(HumanLongProperty.LAST_TURNTABLE_RESET_TIME, lastResetTime);
	}
	
	/**
	 * 获取抽奖信息
	 * 
	 * @param template
	 * @param rate
	 * @return
	 */
	public int[] getRandomRewardInfo(TurntableTemplate template) {
		List<ItemRateData> itemRateDatas = template.getItemRateDatas();
		Map<Integer,MinAndMaxValue> rates = new HashMap<Integer,MinAndMaxValue>();
		
		int nowRate = 0;
		int i = 0;
		for(ItemRateData itemRateData : itemRateDatas) {
			MinAndMaxValue value = new MinAndMaxValue();
			value.setMinValue(nowRate);
			nowRate += itemRateData.getRate();
			value.setMaxValue(nowRate);
			
			rates.put(i, value);
			i++;
		}
		
		if(nowRate <= 0){
			return null;
		}
		
		int rate = MathUtils.randomInt(nowRate);
		int selectIndex = -1;
		for(Integer index : rates.keySet()) {
			MinAndMaxValue value = rates.get(index);
			if(value == null)
				continue;
			if(value.getMinValue() <= rate
					&& rate <= value.getMaxValue()) {
				selectIndex = index;
				break;
			}
		}
		
		if(selectIndex == -1){
			return null;
		}
		else{
			int[] rewardInfo = new int[2];
			rewardInfo[0] = selectIndex;
			rewardInfo[1] = itemRateDatas.get(selectIndex).getItemId();
			return rewardInfo;
		}
	}
	
	public void resetTurntableTime() {
		setTurntableTime(GameServerAssist.getGameConstants().getTurntableFreeTime());
		setTurntableUseCrystalTime(0);
	}

	public void onOpenClick() {
		GCShowTurntablePanel gcMsg = new GCShowTurntablePanel();		
		TurntableTemplate turntableTemplate = GameServerAssist.getTemplateService().get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, TurntableTemplate.class);
		if(turntableTemplate == null){
			return;
		}
		List<ItemRateData> itemRateDatas = turntableTemplate.getItemRateDatas();
		List<CommonItem> items = new ArrayList<CommonItem>();
		for(int i=0; i<itemRateDatas.size(); i++){
			CommonItem item = CommonItemBuilder.genCommonItem(itemRateDatas.get(i).getItemId());
			if(item != null){
				items.add(item);
			}
		}
		gcMsg.setItems(items.toArray(new CommonItem[0]));
		gcMsg.setRewards(GameServerAssist.getTurntableDataService().getRewardInfos());
		gcMsg.setCrystalCost(getTurntableCrystalCost());
		int remainTimes = human.getHumanVipManager().getCurrenctVipTemplate().getCrystalTurntableTimes()-getTurntableUseCrystalTime();
		remainTimes = remainTimes>0 ? remainTimes : 0;
		gcMsg.setCrystalLotteryRemainTimes(remainTimes);
		gcMsg.setWarriorHeartCost(GameServerAssist.getGameConstants().getTurntableCost());
		human.sendMessage(gcMsg);
	}
}
