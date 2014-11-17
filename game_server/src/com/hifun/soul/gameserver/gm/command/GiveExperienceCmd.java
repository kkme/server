package com.hifun.soul.gameserver.gm.command;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 给经验的gm命令
 * 
 * @author magicstone
 *
 */
@Component
public class GiveExperienceCmd implements IAdminCommand<MinaGameClientSession> {

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

		int experience = Integer.parseInt(commands[0]);
		
		try {
			human.addExperience(experience,true,ExperienceLogReason.GM_ADD_EXP, "");
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("Give experience error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_EXPERIENCE;
	}

}
