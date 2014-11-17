package com.hifun.soul.gameserver.battle.action;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.msg.GCDebugChessboardSyncError;
import com.hifun.soul.gameserver.human.Human;

/**
 * 调试使用, 暂停战斗;
 * 
 * @author crazyjohn
 * 
 */
public class PauseBattleHandler implements IInvalidMoveHandler {

	@Override
	public void handleInvalidAction(Human unit) {
		IBattleContext context = unit.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 暂停战斗;
		battle.pauseForInvalidMove(unit);
		GCDebugChessboardSyncError errorMsg = new GCDebugChessboardSyncError();
		battle.broadcastToBattleUnits(errorMsg);
	}

}
