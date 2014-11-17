package com.hifun.soul.gameserver.prison.handler;

import java.util.LinkedList;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonerToInfoConverter;
import com.hifun.soul.gameserver.prison.msg.CGShowLooserTab;
import com.hifun.soul.gameserver.prison.msg.GCShowLooserTab;

@Component
public class CGShowLooserTabHandler implements
		IMessageHandlerWithType<CGShowLooserTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LOOSER_TAB;
	}

	@Override
	public void execute(CGShowLooserTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		GCShowLooserTab msg = new GCShowLooserTab();
		Prisoner prisoner = GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(human.getHumanGuid());
		if (prisoner == null) {
			return;
		}
		if (prisoner.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		LinkedList<Prisoner> looserList = prisoner.getLooserList();
		msg.setLoosers(PrisonerToInfoConverter.convertToArray(looserList));
		human.sendMessage(msg);
	}
}
