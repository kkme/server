package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.msg.CGOpenEscortPanel;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 打开押运面板
 * 
 * @author yandajun
 * 
 */
@Component
public class CGOpenEscortPanelHandler implements
		IMessageHandlerWithType<CGOpenEscortPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_ESCORT_PANEL;
	}

	@Override
	public void execute(CGOpenEscortPanel message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GameServerAssist.getGlobalEscortManager().updateEscortPanel(human);
		// 设置打开板子状态
		human.getHumanEscortManager().setOpenPanel(true);

		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_ESCORT_PANEL.getIndex());
	}

}
