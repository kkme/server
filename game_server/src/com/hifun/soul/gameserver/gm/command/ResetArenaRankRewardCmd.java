package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 重置竞技场排名奖励
 * @author magicstone
 */
public class ResetArenaRankRewardCmd implements IAdminCommand<MinaGameClientSession> {
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
			GameServerAssist.getArenaService().resetArenaRankReward();
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("gm reset arena rank reward error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_RESET_ARENA_RANK_REWARD;
	}

}
