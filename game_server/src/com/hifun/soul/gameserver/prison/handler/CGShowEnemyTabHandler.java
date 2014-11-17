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
import com.hifun.soul.gameserver.prison.msg.CGShowEnemyTab;
import com.hifun.soul.gameserver.prison.msg.GCShowEnemyTab;

@Component
public class CGShowEnemyTabHandler implements
		IMessageHandlerWithType<CGShowEnemyTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_ENEMY_TAB;
	}

	@Override
	public void execute(CGShowEnemyTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		GCShowEnemyTab msg = new GCShowEnemyTab();
		Prisoner prisoner = GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(human.getHumanGuid());
		if (prisoner == null) {
			return;
		}
		if (prisoner.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		LinkedList<Prisoner> enemyList = prisoner.getEnemyList();
		msg.setEnemies(PrisonerToInfoConverter.convertToArray(enemyList));
		human.sendMessage(msg);

	}

}
