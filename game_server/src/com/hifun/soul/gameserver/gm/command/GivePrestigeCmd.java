package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.PrestigeLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 给威望的gm命令
 * 
 * @author magicstone
 * 
 */
public class GivePrestigeCmd implements IAdminCommand<MinaGameClientSession> {

	@Override
	public void execute(MinaGameClientSession playerSession, String[] commands) {
		if (playerSession == null) {
			return;
		}

		Human human = playerSession.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		if (commands.length < 1) {
			return;
		}

		int prestige = Integer.parseInt(commands[0]);
		try {
			human.addPrestige(prestige, true,
					PrestigeLogReason.GM_GET_PRESTIGE, "");
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(e.toString());
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_PRESTIGE;
	}

}
