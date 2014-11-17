package com.hifun.soul.gameserver.bloodtemple.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bloodtemple.msg.CGShowBloodTempleEnemy;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGShowBloodTempleEnemyHandler implements
		IMessageHandlerWithType<CGShowBloodTempleEnemy> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_BLOOD_TEMPLE_ENEMY;
	}

	@Override
	public void execute(CGShowBloodTempleEnemy message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.BLOOD_TEMPLE, true)) {
			return;
		}
		human.getHumanBloodTempleManager().sendBloodTempleEnemyListInfo();
	}

}
