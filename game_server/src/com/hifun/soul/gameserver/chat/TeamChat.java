package com.hifun.soul.gameserver.chat;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.player.Player;


/**
 * 
 * 组队聊天
 * 
 * @author magicstone
 *
 */
public class TeamChat extends AbstractChatStrategy {

	@Override
	protected int getChatInterval() {
		return GameServerAssist.getGameConstants().getTeamChatInterval();
	}

	@Override
	protected int getChatLength() {
		return GameServerAssist.getGameConstants().getTeamChatLength();
	}

	@Override
	protected Player[] getDestPlayers(long passportId) {
		return null;
	}

	@Override
	protected ChatType getChatType() {
		return ChatType.TEAM;
	}

}
