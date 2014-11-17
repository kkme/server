package com.hifun.soul.gameserver.human.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.msg.CGShowCharacterPanel;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowCharacterPanelHandler implements
		IMessageHandlerWithType<CGShowCharacterPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_CHARACTER_PANEL;
	}

	@Override
	public void execute(CGShowCharacterPanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		human.getHumanGuideManager().showGuide(GuideType.OPEN_CHARACTER_PANEL.getIndex());
	}
	
}
