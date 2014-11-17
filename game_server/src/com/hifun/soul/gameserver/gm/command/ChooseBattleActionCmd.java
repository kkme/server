package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 选择战斗行动;
 * 
 * @author crazyjohn
 * 
 */
public class ChooseBattleActionCmd implements
		IAdminCommand<MinaGameClientSession> {

	@Override
	public void execute(MinaGameClientSession session, String[] commands) {
		if (session == null) {
			return;
		}
		
		Human human = session.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		if (!human.isInBattleState()) {
			return;
		}
		IBattleContext context = human.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		if (!battle.isThisUnitTurn(human)) {
			return;
		}
		// 选择一种行动方式
		battle.chooseActionForUnit(human);
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_CHOOSE_BATTLE_ACTION;
	}

}
