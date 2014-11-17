package com.hifun.soul.gameserver.gift.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.gift.manager.HumanGiftManager;
import com.hifun.soul.gameserver.gift.msg.CGActiveGift;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGActiveGiftHandler implements IMessageHandlerWithType<CGActiveGift> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ACTIVE_GIFT;
	}

	@Override
	public void execute(CGActiveGift message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		HumanGiftManager giftManager = human.getHumanGiftManager();
		giftManager.activeGift(message.getGiftId());
	}

}
