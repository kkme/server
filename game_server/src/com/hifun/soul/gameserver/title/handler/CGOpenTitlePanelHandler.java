package com.hifun.soul.gameserver.title.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.title.manager.HumanTitleManager;
import com.hifun.soul.gameserver.title.msg.CGOpenTitlePanel;

@Component
public class CGOpenTitlePanelHandler implements
		IMessageHandlerWithType<CGOpenTitlePanel> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_TITLE_PANEL;
	}

	@Override
	public void execute(CGOpenTitlePanel message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.TITLE, true)) {
			return;
		}
		HumanTitleManager humanTitleManager = human.getHumanTitleManager();
		humanTitleManager.sendHumanTitleInfo();
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_TITLE_PANEL.getIndex());
	}

}
