package com.hifun.soul.gameserver.human.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.EnergyTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.msg.CGBuyEnergy;
import com.hifun.soul.gameserver.human.template.BuyEnergyCostTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.IntPropertyCacheSet;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;

/**
 * 购买体力
 * 
 * @author magicstone
 *
 */
@Component
public class CGBuyEnergyHandler implements IMessageHandlerWithType<CGBuyEnergy> {

	@Autowired
	private VipPrivilegeTemplateManager vipService;
	@Autowired
	private EnergyTemplateManager templateManager;
	
	@Override
	public short getMessageType() {		
		return MessageType.CG_BUY_ENERGY;
	}

	@Override
	public void execute(CGBuyEnergy message) {
		Human human = message.getPlayer().getHuman();
		
		int maxBuyTimes = vipService.getMaxBuyEnergyTimes(human.getVipLevel());
		int buyTimes = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.BUY_ENERGY_TIMES);
		int remainBuyTimes = maxBuyTimes-buyTimes;
		if(remainBuyTimes<=0){
			human.sendErrorMessage(LangConstants.BUY_ENERGY_TIME_USE_OUT);
			return;
		}
		if(human.getEnergy()>=GameServerAssist.getGameConstants().getMaxEnergy()){
			human.sendErrorMessage(LangConstants.NO_NEED_BUY_ENERGY);
			return;
		}
		buyTimes ++;
		remainBuyTimes--;		
		BuyEnergyCostTemplate template = templateManager.getBuyEnergyCostTemplate(buyTimes);
		int costCrystal = template.getCrystalCost();
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, costCrystal)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return;
		}
		human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal, MoneyLogReason.BUY_ENERGY, "");
		IntPropertyCacheSet propertySet = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY);
		//剩余购买体力次数		
		propertySet.setPropertyValue(HumanIntProperty.BUY_ENERGY_TIMES, buyTimes);
		//剩余购买体力次数		
		propertySet.setPropertyValue(HumanIntProperty.BUY_ENERGY_REMAIN_TIMES, remainBuyTimes);
		template = templateManager.getBuyEnergyCostTemplate(buyTimes+1);
		//下次购买体力花费			
		propertySet.setPropertyValue(HumanIntProperty.BUY_ENERGY_CRYSTAL_COST_NUM,
						template.getCrystalCost());
		//下次购买体力获得的点数
		propertySet.setPropertyValue(HumanIntProperty.BUY_ENERGY_ADD_NUM,
				template.getEnergyNum());
		
		human.setEnergy(human.getEnergy()+template.getEnergyNum(),EnergyLogReason.BUY_ENERGY,"buyTimes:"+buyTimes+",remainBuyTime:"+remainBuyTimes);
		
	}

}
