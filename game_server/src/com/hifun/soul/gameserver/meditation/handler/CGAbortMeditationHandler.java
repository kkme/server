package com.hifun.soul.gameserver.meditation.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.msg.CGAbortMeditation;
import com.hifun.soul.gameserver.meditation.msg.GCMeditationComplete;
import com.hifun.soul.gameserver.meditation.msg.GCStartMeditation;

/**
 * 中止冥想
 * 
 * @author magicstone
 *
 */
@Component
public class CGAbortMeditationHandler implements IMessageHandlerWithType<CGAbortMeditation> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_ABORT_MEDITATION;
	}

	@Override
	public void execute(CGAbortMeditation message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		HumanMeditationManager manager = human.getHumanMeditationManager();
		GCMeditationComplete gcMsg = new GCMeditationComplete();
		gcMsg.setTechPoint(manager.getCurrentTechPoint());
		human.getHumanTechnologyManager().addTechnologyPoints(gcMsg.getTechPoint(),true,TechPointLogReason.MEDITATION_ADD_TECH_POINT, "");
		manager.reset();
		human.sendMessage(gcMsg);
		GCStartMeditation gcStartMsg = new GCStartMeditation();
		gcStartMsg.setTotalPoint(manager.getTotalTechPointByMode(manager.getSelectedModeIndex(),manager.getSelectedTimeIndex(),GameServerAssist.getSystemTimeService().now()));
		human.sendMessage(gcStartMsg);
	}

}
