package com.hifun.soul.gameserver.battle;

import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.battle.callback.IBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.human.Human;

/**
 * 基础的战斗回调;
 * 
 * @author crazyjohn
 * 
 */
public abstract class BaseBattleCallback implements IBattleCallback {
	private static final float HALF = 0.5F;
	/** 挑战者 */
	protected Human challenger;
	/** 被挑战者 */
	protected IBattleUnit beChallenged;
	/** 中途退出战斗的单元;规则上认为是loser */
	protected IBattleUnit middleQuitGuy;
	/** 是否战斗回合超时  */
	private boolean isBattleRoundout;

	public BaseBattleCallback(Human challenger) {
		this.challenger = challenger;
	}

	@Override
	public void onBattleFinished() {
		// 调用战斗结束回调接口;
		if (isChallengerWin()) {
			this.onChallengerWin(challenger, beChallenged);
		} else {
			this.onChallengerLose(challenger, beChallenged);
		}
	}

	/**
	 * 挑战者是否战胜;
	 * 
	 * @return
	 */
	private boolean isChallengerWin() {
		// 有人中途退出
		if (this.middleQuitGuy != null) {
			return this.middleQuitGuy != this.challenger;
		}
		// 如果战斗回合超时, 则挑战者失败;
		if (this.isBattleRoundout) {
			return false;
		}
		if (!this.challenger.isDead()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onBattleRoundout() {
		this.isBattleRoundout = true;
	}

	protected abstract void setExitToSceneType(GCExitBattle exitBattle);

	public <U extends IBattleUnit> void setChallengedUnit(U unit) {
		this.beChallenged = unit;
	}

	@Override
	public IBattleUnit getFirstActionUnit() {
		if (this.challenger.getBattleContext().getBattleProperty()
				.getFirstAttack() > this.beChallenged.getBattleContext()
				.getBattleProperty().getFirstAttack()) {
			return challenger;
		} else if (this.challenger.getBattleContext().getBattleProperty()
				.getFirstAttack() < this.beChallenged.getBattleContext()
				.getBattleProperty().getFirstAttack()) {
			return beChallenged;
		} 
		return MathUtils.shake(HALF) ? challenger : beChallenged;
	}

	@Override
	public void middleQuitGuy(IBattleUnit unit) {
		this.middleQuitGuy = unit;
	}
	
	@Override
	public void onEnterBattleFailed() {
		
	}
	
	@Override
	public void onBattleExited() {
		GCExitBattle exitBattle = new GCExitBattle();
		if (isChallengerWin()) {
			exitBattle.setWinnerGuid(this.challenger.getHumanGuid());
		} else {
			exitBattle.setWinnerGuid(this.beChallenged.getUnitGuid());
		}
		// 发送战斗退出消息;在玩家状态机从战斗状态切出以后；
		setExitToSceneType(exitBattle);
		challenger.sendMessage(exitBattle);
		beChallenged.sendMessage(exitBattle);
	}
}
