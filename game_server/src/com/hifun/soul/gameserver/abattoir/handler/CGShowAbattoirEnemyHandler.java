package com.hifun.soul.gameserver.abattoir.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.abattoir.msg.CGShowAbattoirEnemy;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGShowAbattoirEnemyHandler implements
		IMessageHandlerWithType<CGShowAbattoirEnemy> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_ABATTOIR_ENEMY;
	}

	@Override
	public void execute(CGShowAbattoirEnemy message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ABATTOIR, true)) {
			return;
		}
		GameServerAssist.getGlobalAbattoirManager().sendAbattoirEnemyListInfo(
				human);
	}

}
