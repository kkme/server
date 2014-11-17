package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.human.Human;

/**
 * 试练塔战斗回调;
 */
public class BattleWithRefineMonsterCallback extends BaseBattleCallback {
	public BattleWithRefineMonsterCallback(Human human) {
		super(human);
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		challenger.getHumanRefineManager().attackRefineStageWinCallBack();
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		challenger.getHumanRefineManager().attackRefineStageLoseCallBack();
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		// TODO Auto-generated method stub

	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_REFINE_STAGE;
	}
	
	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.REFINE_VIEW
				.getIndex());
	}

}
