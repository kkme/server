package com.hifun.soul.gameserver.battle.counter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCBattleNewRound;
import com.hifun.soul.gameserver.common.GameServerAssist;

/**
 * 战斗回合计数器;
 * 
 * @author crazyjohn
 * 
 */
public class BattleRoundCounter implements IBattleRoundCounter {
	/** 最后行动的单元 */
	private IBattleUnit lastActionUnit;
	/** 当前的回合数 */
	private int currentRound = 1;
	private LinkedList<IRoundListener> listeners = new LinkedList<IRoundListener>();
	/** 是否达到做大回合数 */
	private boolean isReachMaxBattleRound;
	/** 当前战斗 */
	protected Battle battle;

	public BattleRoundCounter(Battle battle) {
		this.battle = battle;
	}

	@Override
	public void recordActionUnit(IBattleUnit actionUnit) {
		doRecord(actionUnit);
	}

	/**
	 * 记录行动逻辑;
	 * 
	 * @param actionUnit
	 */
	private void doRecord(IBattleUnit actionUnit) {
		// 复制数据
		List<IRoundListener> tempListeners = new ArrayList<IRoundListener>();
		for (IRoundListener listener : this.listeners) {
			tempListeners.add(listener);
		}
		// 监听器记录本次行动;
		for (IRoundListener listener : tempListeners) {
			listener.recordAction();
		}
		if (this.lastActionUnit == null) {
			this.lastActionUnit = actionUnit;
			return;
		}
		if (this.lastActionUnit != actionUnit) {
			// 增加回合并置空最后的行动单元;
			this.currentRound++;
			onRoundFinished();
			// 通知client新回合
			notifyClientNewRound();
			this.lastActionUnit = null;
			// 回合增加以后的处理;
			afterRoundAdded(actionUnit);
		}
	}

	/**
	 * 通知client新回合;
	 */
	public void notifyClientNewRound() {
		GCBattleNewRound newRoundMsg = new GCBattleNewRound();
		newRoundMsg.setCurrentRound(this.currentRound);
		battle.broadcastToBattleUnits(newRoundMsg);
	}

	private void afterRoundAdded(IBattleUnit actionUnit) {
		Battle battle = actionUnit.getBattleContext().getBattle();
		if (battle == null) {
			return;
		}
		// 是否达到最大战斗回合数;
		if (!isReachMaxBattleRound(battle)) {
			return;
		}
		// 标记达到最大回合数
		this.isReachMaxBattleRound = true;
	}

	// 是否达到最大的回合数;
	private boolean isReachMaxBattleRound(Battle battle) {
		if (battle.getBattleType() == BattleType.PVP_ARENA
				|| battle.getBattleType() == BattleType.PVP_FRIEND) {
			if (this.currentRound < GameServerAssist.getGameConstants().getMaxPvpBattleRound()) {
				return false;
			}
		} else {
			if (this.currentRound < GameServerAssist.getGameConstants().getMaxPveBattleRound()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 回合结束以后的回调;
	 */
	public void onRoundFinished() {
		// 复制数据
		List<IRoundListener> tempListeners = new ArrayList<IRoundListener>();
		for (IRoundListener listener : this.listeners) {
			tempListeners.add(listener);
		}
		// 第二步, 广播事件;
		for (IRoundListener listener : tempListeners) {
			listener.onRoundFinished();
		}
	}

	@Override
	public int getCurrentRound() {
		return currentRound;
	}

	@Override
	public void recoredActionTimeout(IBattleUnit actionUnit) {
		recordActionUnit(actionUnit);
	}

	@Override
	public void recoredCanNotAction(IBattleUnit actionUnit) {
		recordActionUnit(actionUnit);
	}

	@Override
	public void recordEnemyNewRound(IBattleUnit self) {
		recordActionUnit(self);
		// 判断是否需要通知回合

	}

	@Override
	public void addRoundListener(IRoundListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeRoundListener(IRoundListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public boolean isReachMaxBattleRound() {
		return isReachMaxBattleRound;
	}

	public void setReachMaxBattleRound(boolean isReachMaxBattleRound) {
		this.isReachMaxBattleRound = isReachMaxBattleRound;
	}
}
