package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 重置竞技场战斗次数
 * 
 * @author magicstone
 *
 */
public class ResetArenaBattleTimeCmd implements IAdminCommand<MinaGameClientSession> {

	@Override
	public void execute(MinaGameClientSession playerSession, String[] commands) {
		if (playerSession == null) {
			return;
		}
		
		Human human = playerSession.getPlayer().getHuman();
		if (human == null) {
			return;
		}

		try {
			human.getHumanArenaManager().resetBattleTime();
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("gm reset arena battle time error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_RESET_ARENA_BATTLE_TIME;
	}

}
