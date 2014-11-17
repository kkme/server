package com.hifun.soul.gameserver.battle.msg.schedule;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;

/**
 * 等待客户端播放动画结束反馈超时;
 * 
 * @author crazyjohn
 * 
 */
public class WaitingForAnimationFeedbackTimeoutSchedule extends
		SceneScheduleMessage implements IBattleScheduleMessage {
	private Battle battle;
	private IBeforeAnimationFeedbackCallback callback;
	// 行动方
	IBattleUnit actionUnit;
	// 是否已经反馈
	private boolean oneFeedback = false;
	private boolean otherFeedback = false;

	public WaitingForAnimationFeedbackTimeoutSchedule(IBattleUnit actionUnit,
			Battle battle) {
		this.battle = battle;
		this.actionUnit = actionUnit;
		if (battle.isOneGuyFeedBackBattle()) {
			this.otherFeedback = true;
		}
	}

	@Override
	public void execute() {
		if (this.isCanceled()) {
			return;
		}
		if (this.callback != null) {
			callback.doCall();
		}
		if (battle == null) {
			return;
		}
		battle.onAnimationFeedbackTimeout();
	}

	public void registBeforeAnimationFeedbackCallback(
			IBeforeAnimationFeedbackCallback callback) {
		this.callback = callback;
	}

	public IBeforeAnimationFeedbackCallback getBeforeAnimationFeedbackCallback() {
		return this.callback;
	}

	public interface IBeforeAnimationFeedbackCallback {
		public void doCall();
	}

	@Override
	public Battle getBattle() {
		return battle;
	}

	public boolean isOwner(IBattleUnit unit) {
		return this.actionUnit == unit;
	}

	/**
	 * 是否所有的单元都已经动画回馈完毕;
	 * 
	 * @return
	 */
	public boolean allUnitReady(IBattleUnit unit) {
		this.unitAnimationOver(unit);
		if (this.oneFeedback == true && this.otherFeedback == true) {
			return true;
		}
		return false;
	}

	private void unitAnimationOver(IBattleUnit actionUnit) {
		// 是否是只需要一个动画反馈的战斗;
		if (battle.isOneGuyFeedBackBattle()) {
			this.oneFeedback = true;
			return;
		}
		if (this.actionUnit == actionUnit) {
			this.oneFeedback = true;
		} else {
			this.otherFeedback = true;
		}
	}

}
