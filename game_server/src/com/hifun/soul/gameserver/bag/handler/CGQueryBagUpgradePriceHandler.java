package com.hifun.soul.gameserver.bag.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.msg.CGQueryBagUpgradePrice;
import com.hifun.soul.gameserver.bag.msg.GCBagUpgradePrice;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.player.Player;
@Component
public class CGQueryBagUpgradePriceHandler implements
		IMessageHandlerWithType<CGQueryBagUpgradePrice> {

	@Override
	public short getMessageType() {
		return MessageType.CG_QUERY_BAG_UPGRADE_PRICE;
	}

	@Override
	public void execute(CGQueryBagUpgradePrice message) {
		Player player = message.getSession().getPlayer();
		//目前只升级主背包
		int upgradeLevel = message.getUpgradeLevel();
		BagType bagType = BagType.indexOf(message.getBagType());
		int moneyNum = player.getHuman().getBagManager().queryUpgradePrice(bagType, upgradeLevel) ;
		GCBagUpgradePrice gcMsg = new GCBagUpgradePrice();
		gcMsg.setCurrencyType(CurrencyType.CRYSTAL.getType());
		gcMsg.setPrice(moneyNum);
		player.sendMessage(gcMsg);
	}

}
