package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.msg.CGCloseEscortPanel;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGCloseEscortPanelHandler implements
		IMessageHandlerWithType<CGCloseEscortPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_CLOSE_ESCORT_PANEL;
	}

	@Override
	public void execute(CGCloseEscortPanel message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		human.getHumanEscortManager().setOpenPanel(false);
	}

}
