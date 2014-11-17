package com.hifun.soul.gameserver.bloodtemple.battle;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;

public class BloodTemplePVEBattleCallback extends BaseBattleCallback {
	private int roomId;

	public BloodTemplePVEBattleCallback(Human challenger, int roomId) {
		super(challenger);
		this.roomId = roomId;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalBloodTempleManager().lootNpcCallBack(
				challenger, beChallenged, roomId, true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalBloodTempleManager().lootNpcCallBack(
				challenger, beChallenged, roomId, false);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_BLOOD_TEMPLE_BATTLE;
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalBloodTempleManager()
				.getBloodTempleRoom(roomId).setFighting(true);
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.BLOOD_TEMPLE_VIEW
				.getIndex());
	}

}
