package com.hifun.soul.gameserver.loginreward.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.loginreward.msg.CGShowLoginRewardPanel;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowLoginRewardPanelHandler implements
		IMessageHandlerWithType<CGShowLoginRewardPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LOGIN_REWARD_PANEL;
	}

	@Override
	public void execute(CGShowLoginRewardPanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		human.getHumanLoginRewardManager().onOpenClick();
	}

}
