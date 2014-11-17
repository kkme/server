package com.hifun.soul.gameserver.refine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.refine.msg.CGRefreshRefineMap;

@Component
public class CGRefreshRefineMapHandler implements
		IMessageHandlerWithType<CGRefreshRefineMap> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REFRESH_REFINE_MAP;
	}

	@Override
	public void execute(CGRefreshRefineMap message) {
		Human human = message.getPlayer().getHuman();
		human.getHumanRefineManager().refreshRefineMapHandler(message.getRefineType());
	}

}
