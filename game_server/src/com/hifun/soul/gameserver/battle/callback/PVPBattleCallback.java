package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.human.Human;

/**
 * PVP战斗回调的基类;
 * 
 * @author crazyjohn
 * 
 */
public abstract class PVPBattleCallback extends BaseBattleCallback {
	public PVPBattleCallback(Human challenger) {
		super(challenger);
	}

	/** 是否挑战的是角色本身 */
	private boolean isChallengedHuman;

	public boolean isChallengedHuman() {
		return isChallengedHuman;
	}

	public void setChallengedHuman(boolean isChallengedHuman) {
		this.isChallengedHuman = isChallengedHuman;
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		// do nothing
	}
	
	public boolean requireSendJoinBattleRequest(){
		return true;
	}
	
	/**
	 * 发送pvp战斗邀请类型
	 * @return
	 */
	public void sendJoinBattleRequest(Human beChallenge){
		
	}
}
