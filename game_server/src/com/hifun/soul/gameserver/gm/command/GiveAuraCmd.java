package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.AuraLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 给灵气;
 * 
 * @author crazyjohn
 * 
 */
public class GiveAuraCmd implements IAdminCommand<MinaGameClientSession> {

	@Override
	public void execute(MinaGameClientSession playerSession, String[] commands) {
		if (playerSession == null) {
			return;
		}

		Human human = playerSession.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 给的灵气数量
		int auraNum = Integer.parseInt(commands[0]);
		try {
			human.addAura(auraNum, true, AuraLogReason.GM_GET_AURA, "");
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(String.format("Give aura error"), e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_AURA;
	}

}
