package com.hifun.soul.gameserver.battle.gem.function;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.GemType;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.battle.msg.GCUpdateBlackGemAttackAddRate;
import com.hifun.soul.gameserver.common.GameServerAssist;

/**
 * 强袭功能;
 * 
 * @author crazyjohn
 * 
 */
public class AttackFunctionGems implements IFunctionGems {
	@Override
	public void doEffect(IBattleUnit unit, ChessBoardSnap chessBoardSnap) {
		// 达到最大值直接退出
		if (unit.getBattleContext().getBattleProperty().getAttackAddedRate() >= GameServerAssist.getGameConstants()
				.getBlackMaxAddRate()) {
			return;
		}
		// 获取消除黑宝石的数量
		List<GemPosition> gems = new ArrayList<GemPosition>();

		for (GemPosition gem : chessBoardSnap.getErasableGems()) {
			gems.add(gem);
		}

		int counter = 0;
		for (GemPosition gem : gems) {
			if (gem.getType() == GemType.ATTACK.getIndex()) {
				counter++;
			}
		}
		// 没有宝石的话直接跳出;
		if (counter == 0) {
			return;
		}
		// 触发攻击加成; 整场战斗有效;
		unit.getBattleContext().getBattleProperty().triggerAttackAdd(counter);
		// 通知客户端当前黑宝石加成
		GCUpdateBlackGemAttackAddRate updateRateMsg = new GCUpdateBlackGemAttackAddRate();
		updateRateMsg.setTargetGuid(unit.getUnitGuid());
		updateRateMsg.setCurrentAddRate(unit.getBattleContext()
				.getBattleProperty().getAttackAddedRate());
		unit.getBattleContext().getBattle()
				.broadcastToBattleUnits(updateRateMsg);
	}

}
