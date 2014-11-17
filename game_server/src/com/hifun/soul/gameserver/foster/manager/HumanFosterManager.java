package com.hifun.soul.gameserver.foster.manager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TrainCoinLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.PropertyFosterEvent;
import com.hifun.soul.gameserver.foster.FosterAttribute;
import com.hifun.soul.gameserver.foster.FosterCurrency;
import com.hifun.soul.gameserver.foster.FosterCurrencyType;
import com.hifun.soul.gameserver.foster.FosterMode;
import com.hifun.soul.gameserver.foster.msg.GCSaveFoster;
import com.hifun.soul.gameserver.foster.msg.GCShowFosterPanel;
import com.hifun.soul.gameserver.foster.template.FosterMaxTemplate;
import com.hifun.soul.gameserver.foster.template.FosterModeTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.occupation.template.CharacterPropConverterRateTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.role.properties.manager.PropertyConverterRateManager;

public class HumanFosterManager {
	
	private Human human;
	private FosterTemplateManager templateManager;
	private PropertyConverterRateManager propertyConverterRateManager;
	/** 临时存放的培养值 */
	private int[] tempResult = null;
	private FosterAttribute[] fosterAttribute;
	/** 培养模式比较器 */
	private Comparator<FosterMode> fosterModeSorter = new Comparator<FosterMode>() {
		@Override
		public int compare(FosterMode o1, FosterMode o2) {			
			return o1.getId()-o2.getId();
		}
	};
	
	public HumanFosterManager(Human human){
		this.human = human;		
		templateManager = GameServerAssist.getFosterTemplateManager();
		propertyConverterRateManager = GameServerAssist.getPropertyConverterRateManager();
		fosterAttribute = templateManager.getFosterAttributes();
	}
	
	
	/**
	 * 返回随机出的属性
	 * @return
	 */
	public int[] getRandomForsterValues(int fosterModeId){		
		FosterModeTemplate modeTemplate = templateManager.getFosterModeTemplate(fosterModeId);
		if(modeTemplate == null){
			return null;
		}
		if(human.getVipLevel()<modeTemplate.getOpenVipLevel()){			
			return null;
		}		
		boolean reachMax = true;
		for(FosterAttribute attr : fosterAttribute){
			if(attr.getCurrent()<attr.getMax()){
				reachMax = false;
				break;
			}
		}
		if(reachMax){
			return null;
		}
		
		FosterCurrency cost = templateManager.getFosterCost(human.getLevel(), fosterModeId);
		// 消耗培养币
		if (cost.getType() == FosterCurrencyType.TRAIN_COIN){
			if (!human.costTrainCoin(cost.getNumber(), TrainCoinLogReason.PROPERTY_FOSTER, "")) {
				return null;
			}
		} else {
			// 转化一下货币类型
			CurrencyType currencyType = CurrencyType.indexOf(cost.getType().getIndex());
			if(!human.getWallet().costMoney(currencyType, cost.getNumber(), MoneyLogReason.PROPERTY_FOSTER, "")){
				return null;
			}
		}
		float lowRatio = modeTemplate.getRandomLowRatio()/SharedConstants.DEFAULT_FLOAT_BASE;
		if(lowRatio>1){
			lowRatio = 1;
		}
		Random random = new Random();
		tempResult = new int[fosterAttribute.length];
		int index = 0;
		int randomValue = 0;
		for(FosterAttribute attr : fosterAttribute){
			if(random.nextDouble()<lowRatio){
				randomValue = templateManager.getFosterSuccessValue(fosterModeId, attr.getId());
			}else{
				randomValue = -MathUtils.random(0,attr.getCurrent());
			}
			if(randomValue>attr.getMax()){
				randomValue = attr.getMax();
			}
			tempResult[index] = randomValue;
			index++;
		}
		return tempResult;
	}
	
	/**
	 * 获取一级属性的值
	 * @param level1PropertyId
	 * @return
	 */
	private int getLevel1PropertyValue(int level1PropertyId){
		return human.getPropertyManager().getIntPropertySet(PropertyType.LEVEL1_PROPERTY).getPropertyValue(level1PropertyId);
	}
	
	/**
	 * 设置一级属性的值
	 * @param level1PropertyId
	 * @param value
	 */
	private void setLevel1PropertyValue(int level1PropertyId,int value){
		HumanPropertyManager humanPropertyManager = human.getPropertyManager();		
		if(level1PropertyId == Level1Property.FORCE){
			humanPropertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.FOSTER_FORCE, value);
		}else if(level1PropertyId == Level1Property.AGILE){
			humanPropertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.FOSTER_AGILE, value);
		}else if(level1PropertyId == Level1Property.INTELLIGENCE){
			humanPropertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.FOSTER_INTELLIGENCE, value);
		}else if(level1PropertyId == Level1Property.STAMINA){
			humanPropertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.FOSTER_STAMINA, value);
		}else if(level1PropertyId == Level1Property.SPIRIT){
			humanPropertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.FOSTER_SPIRIT, value);
		}
		
	}
	
	/**
	 * 获取排好序的培养模式
	 * @return
	 */
	private FosterMode[] getFosterModes(){
		FosterMode[] fosterModes = templateManager.getFosterMode(human.getLevel(), human.getVipLevel()).values().toArray(new FosterMode[0]);
		Arrays.sort(fosterModes,fosterModeSorter);
		return fosterModes;
	}
	
	/**
	 * 获取可培养的属性
	 * @return
	 */
	private FosterAttribute[] getFosterAttribute(){
		Map<Integer, CharacterPropConverterRateTemplate> convertMap = propertyConverterRateManager.getOccupationPropConvertRateMap(human.getOccupation().getIndex());
		for(FosterAttribute attr : fosterAttribute){
			attr.setName(convertMap.get(attr.getId()).getLevel1PropName());
			attr.setCurrent(getLevel1PropertyValue(attr.getId()));
			FosterMaxTemplate template = templateManager.getFosterMaxTemplate(attr.getId(), human.getLevel());
			if(template !=null){
				int max = template.getMaxFosterNum();
				attr.setMax(max);
			}
		}
		return fosterAttribute;
	}
	
	/**
	 * 发送更新培养面板消息
	 */
	public void sendShowFosterPanelMessage(){	
		GCShowFosterPanel gcMsg = new GCShowFosterPanel();
		fosterAttribute = getFosterAttribute();
		if(fosterAttribute==null){
			gcMsg.setAttributes(new FosterAttribute[0]);
		}
		else{
			gcMsg.setAttributes(fosterAttribute);
		}		
		gcMsg.setFosterMode(getFosterModes());
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 保持配置的属性
	 */
	public void saveFoster(){
		if(tempResult==null){
			return;
		}
		for(int i=0;i<tempResult.length;i++){
			int value = fosterAttribute[i].getCurrent()+tempResult[i];
			if(value>fosterAttribute[i].getMax()){
				value = fosterAttribute[i].getMax();
			}
			else if(value<0){
				value =0;
			}
			fosterAttribute[i].setCurrent(value);
			setLevel1PropertyValue(fosterAttribute[i].getId(),value);
		}
		GCSaveFoster gcMsg = new GCSaveFoster();
		gcMsg.setAttributes(fosterAttribute);
		human.sendMessage(gcMsg);
		human.getPropertyManager().recalculateInitProperties(human);
		// 发送培养事件
		human.getEventBus().fireEvent(new PropertyFosterEvent());
	}
}
