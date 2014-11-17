package com.hifun.soul.gameserver.horoscope.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.manager.BagTemplateManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeStorageUpgrade;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeStorageUpgrade;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeStorageUpgradeHandler implements
		IMessageHandlerWithType<CGHoroscopeStorageUpgrade> {

	@Autowired
	private BagTemplateManager bagTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_STORAGE_UPGRADE;
	}

	@Override
	public void execute(CGHoroscopeStorageUpgrade message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断开启数量是否合理
		HumanHoroscopeManager horoscopeManager = human.getHumanHoroscopeManager();
		if(horoscopeManager.getHoroscopeStorageSize()+message.getOpenSize() > SharedConstants.HOROSCOPE_STORAGEBAG_SIZE){
			return;
		}
		
		int openSize = horoscopeManager.getHoroscopeStorageSize() - SharedConstants.HOROSCOPE_STORAGE_INIT_SIZE;
		int costNum = bagTemplateManager.getMainBagUpgradePrice(openSize, message.getOpenSize());
		
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, costNum)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return;
		}
		
		if(human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum, MoneyLogReason.HOROSCOPE_STORAGE_UPGRADE, "")){
			horoscopeManager.setHoroscopeStorageSize(horoscopeManager.getHoroscopeStorageSize() + message.getOpenSize());
		}
		
		GCHoroscopeStorageUpgrade gcMsg = new GCHoroscopeStorageUpgrade();
		gcMsg.setOpenSize(horoscopeManager.getHoroscopeStorageSize());
		human.sendMessage(gcMsg);
	}

}
