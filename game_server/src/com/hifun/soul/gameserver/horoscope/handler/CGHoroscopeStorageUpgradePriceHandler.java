package com.hifun.soul.gameserver.horoscope.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.manager.BagTemplateManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeStorageUpgradePrice;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeStorageUpgradePrice;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeStorageUpgradePriceHandler implements
		IMessageHandlerWithType<CGHoroscopeStorageUpgradePrice> {

	@Autowired
	private BagTemplateManager bagTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_STORAGE_UPGRADE_PRICE;
	}

	@Override
	public void execute(CGHoroscopeStorageUpgradePrice message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		HumanHoroscopeManager horoscopeManager = human.getHumanHoroscopeManager();
		if(horoscopeManager.getHoroscopeStorageSize() >= SharedConstants.HOROSCOPE_MAINBAG_SIZE){
			return;
		}
		
		int currentOpenSize = horoscopeManager.getHoroscopeStorageSize() - SharedConstants.HOROSCOPE_STORAGE_INIT_SIZE;
		
		GCHoroscopeStorageUpgradePrice gcMsg = new GCHoroscopeStorageUpgradePrice();
		gcMsg.setCurrencyType((short)CurrencyType.CRYSTAL.getIndex());
		// 跟策划确定星运的存储格子开启规则和背包一样
		gcMsg.setCurrencyNum(bagTemplateManager.getMainBagUpgradePrice(currentOpenSize, message.getOpenSize()));
		gcMsg.setOpenSize(message.getOpenSize());
		human.sendMessage(gcMsg);
	}

}
