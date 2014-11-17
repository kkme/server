package com.hifun.soul.gameserver.abattoir.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.abattoir.msg.CGExtractAbattoirHonor;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGExtractAbattoirHonorHandler implements
		IMessageHandlerWithType<CGExtractAbattoirHonor> {

	@Override
	public short getMessageType() {
		return MessageType.CG_EXTRACT_ABATTOIR_HONOR;
	}

	@Override
	public void execute(CGExtractAbattoirHonor message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ABATTOIR, true)) {
			return;
		}
		GameServerAssist.getGlobalAbattoirManager().extractAbattoirPrestige(human);
	}

}
