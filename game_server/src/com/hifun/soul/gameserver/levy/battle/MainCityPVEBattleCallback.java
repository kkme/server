package com.hifun.soul.gameserver.levy.battle;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.event.MainCityMonsterEvent;
import com.hifun.soul.gameserver.human.Human;

public class MainCityPVEBattleCallback extends BaseBattleCallback {

	public MainCityPVEBattleCallback(Human challenger) {
		super(challenger);
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		addBattleCd(challenger);
		challenger.getLevyManager().mainCityBattleWinCallback();
		fireEvent(challenger);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		addBattleCd(challenger);
		fireEvent(challenger);
	}

	/**
	 * 添加战斗CD
	 */
	private void addBattleCd(Human human) {
		HumanCdManager cdManager = human.getHumanCdManager();
		long spendTime = cdManager.getSpendTime(CdType.MAIN_CITY_BATTLE, 0);
		cdManager.addCd(CdType.MAIN_CITY_BATTLE, spendTime);
		cdManager.snapCdQueueInfo(CdType.MAIN_CITY_BATTLE);
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {

	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_MAIN_CITY;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.TOWN_VIEW.getIndex());
	}

	/**
	 * 发送城堡盗贼战斗事件
	 */
	private void fireEvent(Human challenger) {
		MainCityMonsterEvent event = new MainCityMonsterEvent();
		challenger.getEventBus().fireEvent(event);
	}
}
