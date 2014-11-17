package com.hifun.soul.gameserver.warrior;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.common.GameServerAssist;

public class WarriorOpponent {
	private IBattleUnit battleUnit;
	private WarriorOpponentType opponentType;	
	public IBattleUnit getBattleUnit() {
		return battleUnit;
	}
	public void setBattleUnit(IBattleUnit battleUnit) {
		this.battleUnit = battleUnit;
	}
	public WarriorOpponentType getOpponentType() {
		return opponentType;
	}
	public void setOpponentType(WarriorOpponentType opponentType) {
		this.opponentType = opponentType;
	}
	
	public int getBattleWinRewardNum(){
		int rewardNum = 0;
		switch(opponentType){
		case FRIEND:
			rewardNum = GameServerAssist.getGameConstants().getWarriorChallengeFriendRewardNum();
			break;
		case STRANGER:
			rewardNum = GameServerAssist.getGameConstants().getWarriorChallengeStrangerRewardNum();
			break;
		case NPC:
			rewardNum = GameServerAssist.getGameConstants().getWarriorChallengeNpcRewardNum();
			break;
		}
		return rewardNum;
	}
	
}
