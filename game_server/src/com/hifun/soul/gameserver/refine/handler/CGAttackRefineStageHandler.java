package com.hifun.soul.gameserver.refine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.refine.msg.CGAttackRefineStage;

@Component
public class CGAttackRefineStageHandler implements
		IMessageHandlerWithType<CGAttackRefineStage> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ATTACK_REFINE_STAGE;
	}

	@Override
	public void execute(CGAttackRefineStage message) {
		Human human = message.getPlayer().getHuman();
		human.getHumanRefineManager().attackRefineStageHandler(message.getRefineType());
	}

}
