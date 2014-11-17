package com.hifun.soul.gameserver.bloodtemple.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bloodtemple.msg.CGExtractBloodTempleHonor;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGExtractBloodTempleHonorHandler implements
		IMessageHandlerWithType<CGExtractBloodTempleHonor> {

	@Override
	public short getMessageType() {
		return MessageType.CG_EXTRACT_BLOOD_TEMPLE_HONOR;
	}

	@Override
	public void execute(CGExtractBloodTempleHonor message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.BLOOD_TEMPLE, true)) {
			return;
		}
		GameServerAssist.getGlobalBloodTempleManager().extractBloodTemplePrestige(
				human);
	}

}
