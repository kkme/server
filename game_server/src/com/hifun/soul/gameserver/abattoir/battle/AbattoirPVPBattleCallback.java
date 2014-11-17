package com.hifun.soul.gameserver.abattoir.battle;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.battle.msg.GCJoinBattleRequest;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;

public class AbattoirPVPBattleCallback extends PVPBattleCallback {
	private int roomId;

	public AbattoirPVPBattleCallback(Human challenger, int roomId) {
		super(challenger);
		this.roomId = roomId;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalAbattoirManager().lootPlayerCallBack(
				challenger, beChallenged, roomId, true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalAbattoirManager().lootPlayerCallBack(
				challenger, beChallenged, roomId, false);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_ABATTOIR_BATTLE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.ABATTOIR_VIEW
				.getIndex());
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalAbattoirManager().getAbattoirRoom(roomId)
				.setFighting(true);
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge) {
		GameServerAssist.getGlobalAbattoirManager().getAbattoirRoom(roomId)
				.setFighting(true);
		GCJoinBattleRequest requestBattle = new GCJoinBattleRequest();
		requestBattle.setChallengerGuid(challenger.getHumanGuid());
		requestBattle.setChallengerName(challenger.getName());
		requestBattle.setContent(GameServerAssist.getSysLangService().read(
				LangConstants.ABATTOIR_WRESTLE));
		beChallenge.sendMessage(requestBattle);
	}

}
