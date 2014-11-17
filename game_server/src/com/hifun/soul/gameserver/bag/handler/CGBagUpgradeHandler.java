package com.hifun.soul.gameserver.bag.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.msg.CGBagUpgrade;
import com.hifun.soul.gameserver.bag.msg.GCBagUpgradeResult;
import com.hifun.soul.gameserver.player.Player;
@Component
public class CGBagUpgradeHandler implements IMessageHandlerWithType<CGBagUpgrade> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_BAG_UPGRADE;
	}

	@Override
	public void execute(CGBagUpgrade message) {
		Player player = message.getSession().getPlayer();
		// 目前只升级主背包
		int upgradeLevel = message.getUpgradeLevel();
		boolean result = player.getHuman().getBagManager()
				.upgradeMainBag(upgradeLevel);
		if (result) {
			GCBagUpgradeResult gcMsg = new GCBagUpgradeResult();
			gcMsg.setBagType(message.getBagType());
			gcMsg.setUpgradeLevel(upgradeLevel);
			gcMsg.setLevelSize(SharedConstants.MAIN_BAG_UPDATE_LEVEL_SIZE);
			player.sendMessage(gcMsg);
		}
	}

}
