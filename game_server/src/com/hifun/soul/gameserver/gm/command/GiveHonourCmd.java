package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 给竞技场荣誉的gm命令
 * 
 * @author magicstone
 *
 */
public class GiveHonourCmd implements IAdminCommand<MinaGameClientSession> {

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

		int honour = Integer.parseInt(commands[0]);
		try {			
			human.addArenaHonor(honour, true, HonourLogReason.ARENA_BATTLE_ADD_HONOUR, "");
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(e.toString());
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_HONOUR;
	}

}
