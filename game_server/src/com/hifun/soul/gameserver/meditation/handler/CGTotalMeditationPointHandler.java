package com.hifun.soul.gameserver.meditation.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.msg.CGTotalMeditationPoint;
import com.hifun.soul.gameserver.meditation.msg.GCStartMeditation;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGTotalMeditationPointHandler implements
		IMessageHandlerWithType<CGTotalMeditationPoint> {

	@Override
	public short getMessageType() {
		return MessageType.CG_TOTAL_MEDITATION_POINT;
	}

	@Override
	public void execute(CGTotalMeditationPoint message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		if(!GameServerAssist.getMeditationTemplateManager().checkFuncIsOpen(human, message.getModeIndex(), message.getTimeIndex())){
			return;
		}
		HumanMeditationManager manager = human.getHumanMeditationManager();
		GCStartMeditation gcMsg = new GCStartMeditation();
		gcMsg.setTotalPoint(manager.getTotalTechPointByMode(message.getModeIndex(),message.getTimeIndex(),GameServerAssist.getSystemTimeService().now()));
		human.sendMessage(gcMsg);
	}

}
