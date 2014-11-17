package com.hifun.soul.gameserver.chat;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.player.Player;


/**
 * 附近聊天
 * @author magicstone
 */
public class NearChat extends AbstractChatStrategy {

	@Override
	protected int getChatInterval() {
		return GameServerAssist.getGameConstants().getNearChatInterval();
	}

	@Override
	protected int getChatLength() {
		return GameServerAssist.getGameConstants().getNearChatLength();
	}

	@Override
	protected Player[] getDestPlayers(long passportId) {
		return null;
	}

	@Override
	protected ChatType getChatType() {
		return ChatType.NEAR;
	}

}
