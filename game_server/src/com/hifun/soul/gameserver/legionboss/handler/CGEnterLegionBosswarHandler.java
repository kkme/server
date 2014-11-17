package com.hifun.soul.gameserver.legionboss.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.legionboss.msg.CGEnterLegionBossWar;

@Component
public class CGEnterLegionBosswarHandler implements
		IMessageHandlerWithType<CGEnterLegionBossWar> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ENTER_LEGION_BOSS_WAR;
	}

	@Override
	public void execute(CGEnterLegionBossWar message) {
		message.getPlayer().getHuman().getHumanLegionBossManager().onOpenClick();
	}

}
