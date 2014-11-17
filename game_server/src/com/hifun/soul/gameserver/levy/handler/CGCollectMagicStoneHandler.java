package com.hifun.soul.gameserver.levy.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.levy.HumanLevyManager;
import com.hifun.soul.gameserver.levy.msg.CGCollectMagicStone;

@Component
public class CGCollectMagicStoneHandler implements
		IMessageHandlerWithType<CGCollectMagicStone> {

	@Override
	public short getMessageType() {
		return MessageType.CG_COLLECT_MAGIC_STONE;
	}

	@Override
	public void execute(CGCollectMagicStone message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MAIN_CITY_AURA, true)) {
			return;
		}
		HumanLevyManager levyManager = human.getLevyManager();
		levyManager.handleCollectMagicStone();

	}

}
