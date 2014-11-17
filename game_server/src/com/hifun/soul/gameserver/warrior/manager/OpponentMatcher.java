package com.hifun.soul.gameserver.warrior.manager;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.warrior.WarriorOpponent;
import com.hifun.soul.gameserver.warrior.WarriorOpponentType;

/**
 * 对手匹配器
 * 
 * @author magicstone
 * 
 */
public class OpponentMatcher {
	public static WarriorOpponent getOpponent(Human human) {
		WarriorOpponent opponent = new WarriorOpponent();
		List<IBattleUnit> suitableHumans = new ArrayList<IBattleUnit>();
		int levelDiff = GameServerAssist.getGameConstants().getWarriorChallengeLevelDiff();
		for (Human otherHuman : GameServerAssist.getGameWorld().getSceneHumanManager().getAllHumans()) {
			if (suitableHumans.size() >= SharedConstants.WARRIOR_OPPONENT_POOL_SIZE) {
				break;
			}
			if (otherHuman.getHumanGuid() == human.getHumanGuid()) {
				continue;
			}
			if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(otherHuman, GameFuncType.WARRIOR_WAY, false)) {
				continue;
			}
			if (Math.abs(otherHuman.getLevel() - human.getLevel()) <= levelDiff) {
				suitableHumans.add(otherHuman);
			}
		}
		suitableHumans.addAll(GameServerAssist.getMonsterFactory().getWarriorNpc(human.getLevel()));
		if (suitableHumans.size() == 0) {
			return null;
		}
		IBattleUnit battleUnit = suitableHumans.get(MathUtils.random(0, suitableHumans.size() - 1));
		opponent.setBattleUnit(battleUnit);
		if (battleUnit instanceof Human) {
			if (GameServerAssist.getFriendService().isFriend(human.getHumanGuid(), battleUnit.getUnitGuid())) {
				opponent.setOpponentType(WarriorOpponentType.FRIEND);
			} else {
				opponent.setOpponentType(WarriorOpponentType.STRANGER);
			}
		} else {
			opponent.setOpponentType(WarriorOpponentType.NPC);
		}
		return opponent;
	}
}
