package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 给星魂;
 * 
 * @author crazyjohn
 * 
 */
public class GiveStarSoulCmd implements IAdminCommand<MinaGameClientSession> {

	@Override
	public void execute(MinaGameClientSession playerSession, String[] commands) {
		if (playerSession == null) {
			return;
		}

		Human human = playerSession.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 给的星魂数量
		int starSoulNum = Integer.parseInt(commands[0]);
		try {
			human.setStarSoul(human.getStarSoul() + starSoulNum);
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(String.format("Give starSoul error"), e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_STAR_SOUL;
	}

}
