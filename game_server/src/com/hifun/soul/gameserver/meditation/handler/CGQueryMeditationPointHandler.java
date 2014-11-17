package com.hifun.soul.gameserver.meditation.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.msg.CGQueryMeditationPoint;
import com.hifun.soul.gameserver.meditation.msg.GCQueryMeditationPoint;

@Component
public class CGQueryMeditationPointHandler implements IMessageHandlerWithType<CGQueryMeditationPoint> {

	@Override
	public short getMessageType() {
		return MessageType.CG_QUERY_MEDITATION_POINT;
	}

	@Override
	public void execute(CGQueryMeditationPoint message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		HumanMeditationManager manager = human.getHumanMeditationManager();
		GCQueryMeditationPoint gcMsg = new GCQueryMeditationPoint();
		gcMsg.setTechPoint(manager.getCurrentTechPoint());
		human.sendMessage(gcMsg);
	}

}
