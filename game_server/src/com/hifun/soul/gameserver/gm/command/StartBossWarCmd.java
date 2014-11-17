package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.activity.ActivityState;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 开启boss战
 * 
 * @author magicstone
 *
 */
public class StartBossWarCmd implements IAdminCommand<MinaGameClientSession> {
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
			GameServerAssist.getGlobalActivityManager().getActivity(ActivityType.BOSS_WAR).setState(ActivityState.OPEN);
			GameServerAssist.getGlobalActivityManager().getActivity(ActivityType.BOSS_WAR).setNextTimeSpanIndex(0);
			GameServerAssist.getBossWarService().onStart();
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("start BossWar error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_START_BOSS_WAR;
	}

}
