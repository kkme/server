package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;



public class ClearBagCmd implements IAdminCommand<MinaGameClientSession> {

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
			human.getBagManager().clear(BagType.MAIN_BAG, ItemLogReason.CLEAR_BAG, "");
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("gm clear bag error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_CLEAR_BAG;
	}

}
