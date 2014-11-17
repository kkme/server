package com.hifun.soul.gameserver.refine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.refine.msg.CGAttackAllRefineStage;

@Component
public class CGAttackAllRefineStageHandler implements
		IMessageHandlerWithType<CGAttackAllRefineStage> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ATTACK_ALL_REFINE_STAGE;
	}

	@Override
	public void execute(CGAttackAllRefineStage message) {
		Human human = message.getPlayer().getHuman();
		human.getHumanRefineManager().attackAllRefineStageHandler(message.getRefineType());
	}

}
