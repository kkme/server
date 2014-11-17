package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 重置竞技场排名奖励
 * @author magicstone
 */
public class ChangeArenaRankCmd implements IAdminCommand<MinaGameClientSession> {
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
		int toRank = Integer.parseInt(commands[0]);
		ArenaMember arenaMember = GameServerAssist.getArenaService().getArenaMember(human.getHumanGuid());
		if(arenaMember == null){
			return;
		}
		try {
			GameServerAssist.getArenaService().changeArenaMemberRank(arenaMember.getRank(), toRank);
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("gm change arena rank error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_CHANGE_ARENA_RANK;
	}

}
