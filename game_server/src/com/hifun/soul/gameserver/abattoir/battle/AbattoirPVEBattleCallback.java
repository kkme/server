package com.hifun.soul.gameserver.abattoir.battle;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;

public class AbattoirPVEBattleCallback extends BaseBattleCallback {
	private int roomId;

	public AbattoirPVEBattleCallback(Human challenger, int roomId) {
		super(challenger);
		this.roomId = roomId;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalAbattoirManager().lootNpcCallBack(challenger,
				beChallenged, roomId, true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalAbattoirManager().lootNpcCallBack(challenger,
				beChallenged, roomId, false);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_ABATTOIR_BATTLE;
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalAbattoirManager().getAbattoirRoom(roomId)
				.setFighting(true);
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.ABATTOIR_VIEW
				.getIndex());
	}

}
