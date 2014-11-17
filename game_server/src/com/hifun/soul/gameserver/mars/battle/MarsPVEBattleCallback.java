package com.hifun.soul.gameserver.mars.battle;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.human.Human;

public class MarsPVEBattleCallback extends BaseBattleCallback {
	private Human human;
	private int roomType;
	private int multiple;

	public MarsPVEBattleCallback(Human challenger, int roomType, int multiple) {
		super(challenger);
		human = challenger;
		this.roomType = roomType;
		this.multiple = multiple;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		human.getHumanMarsManager().killCallBack(roomType, true, multiple);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		human.getHumanMarsManager().killCallBack(roomType, false, multiple);

	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {

	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_MARS_BATTLE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.MARS_VIEW.getIndex());
	}

}
