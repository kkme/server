package com.hifun.soul.gameserver.battle.gem.filter;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;

/**
 * 4连消多一回合;
 * 
 * @author crazyjohn
 * 
 */
public class FourSameColorFilter implements IGemEraseFilter {

	@Override
	public void doFilter(List<ChessBoardSnap> snaps, IBattleUnit unit) {
		if (!triggerNewRound(snaps)) {
			return;
		}
		IBattleContext context = unit.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		battle.addNewRoundToUnit(unit);
		// editby:crazyjohn 注释,改而使用ChessBoardSnap的机制;
		// 通知战斗单元
//		GCAddBattleActionTime addMsg = new GCAddBattleActionTime();
//		addMsg.setUnitGuid(unit.getUnitGuid());
//		battle.broadcastToBattleUnits(addMsg);
	}

	// 是否触发新回合
	private boolean triggerNewRound(List<ChessBoardSnap> snaps) {
		for (ChessBoardSnap snap : snaps) {
			if (snap.getTriggerNewRound()) {
				return true;
			}
		}
		return false;
	}

}
