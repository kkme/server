package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 清空体力值;
 * 
 * @author crazyjohn
 * 
 */
public class ClearEngergyCmd implements IAdminCommand<MinaGameClientSession> {

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
			human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(HumanIntProperty.ENERGY, 0);
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("gm clear bag error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_CLEAR_ENERGY;
	}

}
