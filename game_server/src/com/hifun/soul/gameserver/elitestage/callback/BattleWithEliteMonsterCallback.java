package com.hifun.soul.gameserver.elitestage.callback;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.event.BattleWinEvent;
import com.hifun.soul.gameserver.event.EliteStageBattleEvent;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;

public class BattleWithEliteMonsterCallback extends BaseBattleCallback {
	private int currentStageId;

	public BattleWithEliteMonsterCallback(Human human, Monster monster,
			int stageId) {
		super(human);
		this.beChallenged = monster;
		currentStageId = stageId;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		this.challenger.getHumanEliteStageManager().updateBattleResult(
				this.currentStageId, true);
		EliteStageBattleEvent battleWinEvent = new EliteStageBattleEvent(true);
		this.challenger.getEventBus().fireEvent(battleWinEvent);
		// 发送战斗胜利事件
		BattleWinEvent battleWin = new BattleWinEvent();
		this.challenger.getEventBus().fireEvent(battleWin);
		if(this.beChallenged instanceof Monster
				&& ((Monster)this.beChallenged).getTemplate().getId() == 20101){
			challenger.getHumanGuideManager().showGuide(GuideType.ELITE_20101.getIndex());
		}
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		this.challenger.getHumanEliteStageManager().updateBattleResult(
				this.currentStageId, false);
		EliteStageBattleEvent battleWinEvent = new EliteStageBattleEvent(false);
		this.challenger.getEventBus().fireEvent(battleWinEvent);
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {

	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_ELITE_STAGE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.ELITE_STAGE_VIEW
				.getIndex());
	}

}
