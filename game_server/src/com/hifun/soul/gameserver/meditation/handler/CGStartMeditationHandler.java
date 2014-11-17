package com.hifun.soul.gameserver.meditation.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.meditation.msg.CGStartMeditation;

@Component
public class CGStartMeditationHandler implements IMessageHandlerWithType<CGStartMeditation> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_START_MEDITATION;
	}

	@Override
	public void execute(CGStartMeditation message) {
		if(message.getModeIndex()<0 || message.getTimeIndex()<0){
			return;
		}
		message.getPlayer().getHuman().getHumanMeditationManager().startMeditation(message.getModeIndex(),message.getTimeIndex());		
	}

}
