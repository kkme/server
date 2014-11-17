package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 给勇者之心的gm命令
 * @author magicstone
 */
public class GiveWarriorCmd implements IAdminCommand<MinaGameClientSession> {

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
		int num = Integer.parseInt(commands[0]);
		try {			
			human.getHumanWarriorManager().addWarriorHeartNum(num);
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(String.format(
					"Give warrior error, num: %d", num), e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_WARRIOR;
	}

}
