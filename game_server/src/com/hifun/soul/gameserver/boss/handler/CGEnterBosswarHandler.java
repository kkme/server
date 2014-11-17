package com.hifun.soul.gameserver.boss.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.boss.msg.CGEnterBossWar;

@Component
public class CGEnterBosswarHandler implements 
	IMessageHandlerWithType<CGEnterBossWar> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ENTER_BOSS_WAR;
	}

	@Override
	public void execute(CGEnterBossWar message) {
		message.getPlayer().getHuman().getHumanBossManager().onOpenClick();
	}

}
