package com.hifun.soul.gameserver.warrior.battle;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.warrior.manager.HumanWarriorManager;

public class WarriorPVEBattleCallback extends BaseBattleCallback {
	private int winRewardWarriorHeartNum;
	private int orignalHp = 0;
	public WarriorPVEBattleCallback(Human challenger,int winBattleRewardNum) {
		super(challenger);	
		winRewardWarriorHeartNum = winBattleRewardNum;
	}
	
	

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		HumanWarriorManager warriorManager = challenger.getHumanWarriorManager();
		warriorManager.refreshOpponent(true);
		warriorManager.sendUpdateOpponentMessage();
		warriorManager.addWarriorHeartNum(winRewardWarriorHeartNum);
		warriorManager.updateQuestCounter(1);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		HumanWarriorManager warriorManager = challenger.getHumanWarriorManager();
		warriorManager.refreshOpponent(true);
		warriorManager.sendUpdateOpponentMessage();
		int damage = orignalHp-beChallenged.getBattleContext().getBattleProperty().getHp();		
		warriorManager.updateQuestCounter(damage/(orignalHp*1f));
	}	


	@Override
	public BattleType getBattleType() {		
		return BattleType.PVE_WARRIOR_BATTLE;
	}


	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		orignalHp = beChallenged.getBattleContext().getBattleProperty().getHp();
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		// do nothing
		
	}
	
}
