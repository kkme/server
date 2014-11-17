package com.hifun.soul.gameserver.battle;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.gameserver.battle.counter.IBattleRoundCounter;

/**
 * 战斗行动调度中心;
 * 
 * @author crazyjohn
 * 
 */
public class BattleActionScheduleCenter implements IBattleActionScheduleCenter {
	// 战斗单元
	protected IBattleUnit oneGuy;
	protected IBattleUnit otherGuy;
	private Battle battle;
	/** 上一次行动的单元 */
	private IBattleUnit lastActionUnit;
	/** 每个角色额外添加的战斗行动次数 */
	private Map<Long, Integer> unitAddedAtionTimes = new HashMap<Long, Integer>();
	/** 战斗回合计数器 */
	private IBattleRoundCounter roundCounter;

	public BattleActionScheduleCenter(IBattleUnit oneGuy, IBattleUnit otherGuy,
			Battle battle, IBattleRoundCounter roundCounter) {
		this.oneGuy = oneGuy;
		this.otherGuy = otherGuy;
		this.battle = battle;
		this.roundCounter = roundCounter;
		// 初始化额外行动次数
		this.unitAddedAtionTimes.put(oneGuy.getUnitGuid(), 0);
		this.unitAddedAtionTimes.put(otherGuy.getUnitGuid(), 0);
	}

	@Override
	public IBattleUnit getNextActionUnit() {
		// 最后的行动者是否完成了行动;
		if (!this.lastActionUnit.isCurrentActionFinished()) {
			return lastActionUnit;
		}
		// 是否有额外行动
		if (getAddedActionTimes(this.lastActionUnit) != 0) {
			// 重置最后行动单元的状态;
			this.lastActionUnit.resetFinishActionState();
			reduceAddedActionTimes(this.lastActionUnit);
			// 记录敌方无法行动;但是要执行敌方身上的预执行buff;
			jumpOtherGuyAction();
			return lastActionUnit;
		}
		// 获取下一个行动者;
		IBattleUnit result = null;
		if (this.oneGuy == this.lastActionUnit) {
			result = this.otherGuy;
		} else {
			result = this.oneGuy;
		}
		this.lastActionUnit = result;
		// 重新设置行动状态;
		result.resetFinishActionState();
		return result;
	}

	/**
	 * 跳过的行动单元处理逻辑;
	 */
	private void jumpOtherGuyAction() {
		IBattleUnit self = ((this.oneGuy == this.lastActionUnit) ? this.otherGuy
				: this.oneGuy);
		// 执行中毒或者加血等预执行buff
		self.getBattleContext().getBuffManager().beforeAction();
		// 记录回合
		this.roundCounter.recordEnemyNewRound(self);
	}

	private void reduceAddedActionTimes(IBattleUnit unit) {
		this.unitAddedAtionTimes.put(unit.getUnitGuid(),
				this.unitAddedAtionTimes.get(unit.getUnitGuid()) - 1);
	}

	private int getAddedActionTimes(IBattleUnit unit) {
		return this.unitAddedAtionTimes.get(unit.getUnitGuid());
	}

	/**
	 * 添加行动次数;
	 * 
	 * @param unit
	 */
	@Override
	public void addActionTimes(IBattleUnit unit) {
		int times = this.unitAddedAtionTimes.get(unit.getUnitGuid());
		++times;
		unitAddedAtionTimes.put(unit.getUnitGuid(), times);
	}

	@Override
	public boolean isThisUnitTurn(IBattleUnit unit) {
		if (this.getNextActionUnit() == unit) {
			return true;
		}
		return false;
	}

	@Override
	public void turnToNextUnitAction() {
		// 如果战斗结束, 直接跳出;
		if (battle.getCurrentState() == BattleState.FINISHED) {
			return;
		}
		// 获取下一个行动单元;
		IBattleUnit unit = getNextActionUnit();
		if (unit == null) {
			throw new NullPointerException("Next battle unit can't be null");
		}

		// 无论此战斗单元是否可以行动(比如战斗单元被眩晕), 都要先执行一遍buff管理器的预处理;
		unit.getBattleContext().getBuffManager().beforeAction();

		// 下一个单元执行行动;如果下一个单元无法行动, 则直接跳过;
		if (unit.canAction()) {
			unit.notifyAction();
		} else {
			// 记录无法行动
			this.roundCounter.recoredCanNotAction(unit);
			// 直接结束行动;并转到下一个行动单元;
			unit.finishCurrentAction();
			turnToNextUnitAction();
		}
	}

	@Override
	public void startFirstAction(IBattleUnit firstActionUnit) {
		this.lastActionUnit = firstActionUnit;
	}

}
