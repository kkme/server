package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.SkillPointLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 给技能点的gm命令
 * 
 * @author magicstone
 *
 */
public class GiveSkillPointCmd implements IAdminCommand<MinaGameClientSession> {

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

		int points = Integer.parseInt(commands[0]);
		
		try {
			human.addSkillPoint(points,true,SkillPointLogReason.GM_GET_SKILL_POINT, "");
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("Give skill point error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_SKILL_POINT;
	}

}
