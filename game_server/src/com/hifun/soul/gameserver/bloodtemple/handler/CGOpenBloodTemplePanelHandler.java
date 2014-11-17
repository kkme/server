package com.hifun.soul.gameserver.bloodtemple.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bloodtemple.msg.CGOpenBloodTemplePanel;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGOpenBloodTemplePanelHandler implements
		IMessageHandlerWithType<CGOpenBloodTemplePanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_BLOOD_TEMPLE_PANEL;
	}

	@Override
	public void execute(CGOpenBloodTemplePanel message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.BLOOD_TEMPLE, true)) {
			return;
		}
		// 军衔等级是否达到
		int needTitle = GameServerAssist.getGameConstants()
				.getBloodTempleOpenTitle();
		if (human.getCurrentTitle() < needTitle) {
			String needTitleName = GameServerAssist.getTitleTemplateManager()
					.getHumanTitleTemplate(needTitle).getTitleName();
			human.sendWarningMessage(LangConstants.BLOOD_TEMPLE_OPEN_TITLE,
					needTitleName);
			return;
		}
		GameServerAssist.getGlobalBloodTempleManager().sendPanelInfo(human);
	}
}
