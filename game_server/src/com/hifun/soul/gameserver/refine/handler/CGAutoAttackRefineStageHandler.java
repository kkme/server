package com.hifun.soul.gameserver.refine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.refine.msg.CGAutoAttackRefineStage;

@Component
public class CGAutoAttackRefineStageHandler implements
		IMessageHandlerWithType<CGAutoAttackRefineStage> {

	@Override
	public short getMessageType() {
		return MessageType.CG_AUTO_ATTACK_REFINE_STAGE;
	}

	@Override
	public void execute(CGAutoAttackRefineStage message) {
		Human human = message.getPlayer().getHuman();
		human.getHumanRefineManager().autoAttackRefineStageHandler(message.getRefineType());
	}

}
