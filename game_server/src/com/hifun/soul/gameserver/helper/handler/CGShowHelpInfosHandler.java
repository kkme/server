package com.hifun.soul.gameserver.helper.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.helper.msg.CGShowHelpInfos;
import com.hifun.soul.gameserver.helper.msg.GCShowHelpInfos;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowHelpInfosHandler implements
		IMessageHandlerWithType<CGShowHelpInfos> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_HELP_INFOS;
	}

	@Override
	public void execute(CGShowHelpInfos message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		GCShowHelpInfos gcMsg = new GCShowHelpInfos();
		gcMsg.setHelpInfoList(human.getHumanHelperManager().getHelpInfos());
		gcMsg.setIsNeedOpen(message.getIsNeedOpen());
		human.sendMessage(gcMsg);
	}

}
