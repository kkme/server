package com.hifun.soul.gameserver.bloodtemple.battle;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.battle.msg.GCJoinBattleRequest;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;

public class BloodTemplePVPBattleCallback extends PVPBattleCallback {
	private int roomId;

	public BloodTemplePVPBattleCallback(Human challenger, int roomId) {
		super(challenger);
		this.roomId = roomId;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalBloodTempleManager().lootPlayerCallBack(
				challenger, beChallenged, roomId, true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalBloodTempleManager().lootPlayerCallBack(
				challenger, beChallenged, roomId, false);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_BLOOD_TEMPLE_BATTLE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.BLOOD_TEMPLE_VIEW
				.getIndex());
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalBloodTempleManager()
				.getBloodTempleRoom(roomId).setFighting(true);
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge) {
		GameServerAssist.getGlobalBloodTempleManager()
				.getBloodTempleRoom(roomId).setFighting(true);
		GCJoinBattleRequest requestBattle = new GCJoinBattleRequest();
		requestBattle.setChallengerGuid(challenger.getHumanGuid());
		requestBattle.setChallengerName(challenger.getName());
		requestBattle.setContent(GameServerAssist.getSysLangService().read(
				LangConstants.BLOOD_TEMPLE_WRESTLE));
		beChallenge.sendMessage(requestBattle);
	}

}
