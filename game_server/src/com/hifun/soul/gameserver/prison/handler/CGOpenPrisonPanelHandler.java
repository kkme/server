package com.hifun.soul.gameserver.prison.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.msg.CGOpenPrisonPanel;

/**
 * 打开战俘营面板
 * 
 * @author yandajun
 * 
 */
@Component
public class CGOpenPrisonPanelHandler implements
		IMessageHandlerWithType<CGOpenPrisonPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_PRISON_PANEL;
	}

	@Override
	public void execute(CGOpenPrisonPanel message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		GameServerAssist.getGlobalPrisonManager().openPrisonPanel(human);
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_PRISON_PANEL.getIndex());
	}
}
