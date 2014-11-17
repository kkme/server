package com.hifun.soul.gameserver.stage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.stage.msg.CGAutoAttackStage;

@Component
public class CGAutoAttackStageHandler implements
		IMessageHandlerWithType<CGAutoAttackStage> {

	@Override
	public short getMessageType() {
		return MessageType.CG_AUTO_ATTACK_STAGE;
	}

	@Override
	public void execute(CGAutoAttackStage message) {
		
	}

}
