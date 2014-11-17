package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 重置属性点
 * 
 * @author magicstone
 *
 */
public class ResetHumanPropertiesCmd implements IAdminCommand<MinaGameClientSession> {

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
			int totalPoints= 0;
			// 把系统分配的刨除
			int newPower = human.getPower()-human.getSystemPower();
			human.setPower(newPower);
			totalPoints += human.getSystemPower();
			human.setSystemPower(0);
			int newAgile = human.getAgile() - human.getSystemAgile();
			human.setAgile(newAgile);
			totalPoints += human.getSystemAgile();
			human.setSystemAgile(0);
			int newStamina = human.getStamina() - human.getSystemtStamina();
			human.setStamina(newStamina);
			totalPoints += human.getSystemtStamina();
			human.setSystemStamina(0);
			int newIntelligence = human.getIntelligence() - human.getSystemIntelligence();
			human.setIntelligence(newIntelligence);
			totalPoints += human.getSystemIntelligence();
			human.setSystemIntelligence(0);
			int newSpirit = human.getSpirit() - human.getSystemSpirit();
			human.setSpirit(newSpirit);
			totalPoints += human.getSystemSpirit();
			human.setSystemSpirit(0);
			// 把系统分配的还原为未分配的
			human.setUnDistributePropertyPoint(human.getUnDistributePropertyPoint() + totalPoints);
			// 重新计算属性
			human.getPropertyManager().recalculateInitProperties(human);
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("gm reset human properties time error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_RESET_HUMAN_PROPERTIES;
	}

}
