package com.hifun.soul.gameserver.chat;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.player.Player;


/**
 * 帮派聊天
 * @author magicstone
 */
public class GuildChat extends AbstractChatStrategy{

	@Override
	protected int getChatInterval() {
		return GameServerAssist.getGameConstants().getGuildChatInterval();
	}

	@Override
	protected int getChatLength() {
		return GameServerAssist.getGameConstants().getGuildChatLength();
	}

	@Override
	protected Player[] getDestPlayers(long passportId) {
		return null;
	}

	@Override
	protected ChatType getChatType() {
		return ChatType.GUILD;
	}

}
