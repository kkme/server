package com.hifun.soul.gameserver.mine.callback;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;

public class BattleWhenOpenMineCallback extends BaseBattleCallback {

	public BattleWhenOpenMineCallback(Human human, Monster monster) {
		super(human);
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		challenger.getHumanMineManager().onEncounterBattleEnd(true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		challenger.getHumanMineManager().onEncounterBattleEnd(false);
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		// do nothing

	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_MINE_ENCOUNTER;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.MINE_VIEW.getIndex());
	}

}
