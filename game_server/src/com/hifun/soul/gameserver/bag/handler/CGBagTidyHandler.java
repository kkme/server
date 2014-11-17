package com.hifun.soul.gameserver.bag.handler;

import org.springframework.stereotype.Component;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.msg.CGBagTidy;
import com.hifun.soul.gameserver.player.Player;
@Component
public class CGBagTidyHandler implements IMessageHandlerWithType<CGBagTidy> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_BAG_TIDY;
	}

	@Override
	public void execute(CGBagTidy message) {
		Player player = message.getSession().getPlayer();
		BagType bagType =  BagType.indexOf(message.getBagType());
		player.getHuman().getBagManager().tidy(bagType);			
	}

}
