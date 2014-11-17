package com.hifun.soul.gameserver.skill.buff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hifun.soul.gameserver.battle.counter.AbstractRoudListener;
import com.hifun.soul.gameserver.skill.msg.GCReplaceOldBuff;

/**
 * 战斗单元buff管理器;
 * 
 * @author crazyjohn
 * 
 */
public class BattleUnitBuffManager extends AbstractRoudListener {
	private Map<BuffType, LinkedList<IBuff>> allBuffs = new HashMap<BuffType, LinkedList<IBuff>>();
	private static int counter = 0;

	/**
	 * 生成buffId;
	 * 
	 * @return
	 */
	public int generateBuffId() {
		return ++counter;
	}

	/**
	 * 当每轮战斗结束时候的回调接口;
	 */
	@Override
	public void onRoundFinished() {
		for (List<IBuff> buffs : allBuffs.values()) {
			for (IBuff buff : buffs) {
				buff.onRoundFinished();
			}
		}
		List<IBuff> removeBuffes = this.getNeedRemoveBuffes();
		for (IBuff buff : removeBuffes) {
			this.remove(buff);
		}
	}

	/**
	 * 添加指定的buff;
	 * 
	 * @param buff
	 */
	public void addBuff(IBuff buff) {
		LinkedList<IBuff> exitBuffs = this.allBuffs.get(buff.getType());
		if (exitBuffs == null) {
			exitBuffs = new LinkedList<IBuff>();
			this.allBuffs.put(buff.getType(), exitBuffs);
		}
		// 1. 判断是否可叠加
		if (exitBuffs.size() == 1) {
			if (buff.isOverlapable()) {
				// 如果可叠加
				exitBuffs.add(buff);
				buff.onAdd(true);
			} else {
				// 如果不可叠加;把旧buff去掉, 添加新buff; 需要客户端配合处理;
				GCReplaceOldBuff replaceMsg = new GCReplaceOldBuff();
				replaceMsg.setTargetGuid(buff.getTarget().getUnitGuid());
				replaceMsg.setOldBuffId(exitBuffs.getFirst().getBuffId());
				replaceMsg.setNewBuff(buff.toBuffInfo());
				buff.getTarget().getBattleContext().getBattle()
						.broadcastToBattleUnits(replaceMsg);
				// 维护逻辑
				for (IBuff exitBuff : exitBuffs) {
					exitBuff.onRemove(false);
				}
				exitBuffs.clear();
				exitBuffs.add(buff);
				buff.onAdd(false);
			}
			return;
		} else {
			// 可以叠加的情况
			if (exitBuffs.size() >= buff.getOverlyingCount()) {
				// 达到最大叠加次数; 覆盖掉第一个效果相同的buff; 需要客户端配合处理;
				GCReplaceOldBuff replaceMsg = new GCReplaceOldBuff();
				replaceMsg.setTargetGuid(buff.getTarget().getUnitGuid());
				replaceMsg.setOldBuffId(exitBuffs.getFirst().getBuffId());
				replaceMsg.setNewBuff(buff.toBuffInfo());
				buff.getTarget().getBattleContext().getBattle()
						.broadcastToBattleUnits(replaceMsg);
				// 维护逻辑
				exitBuffs.getFirst().onRemove(false);
				exitBuffs.removeFirst();
				exitBuffs.addFirst(buff);
				buff.onAdd(false);
			} else {
				// 没有达到最大叠加次数
				exitBuffs.add(buff);
				buff.onAdd(true);
			}
		}
	}

	/**
	 * 移除指定的buff;
	 * 
	 * @param buff
	 */
	private void remove(IBuff buff) {
		if (buff != null) {
			buff.onRemove(true);
			LinkedList<IBuff> buffs = this.allBuffs.get(buff.getType());
			Iterator<IBuff> it = buffs.iterator();
			while (it.hasNext()) {
				IBuff eachBuff = it.next();
				if (eachBuff.getBuffId() == buff.getBuffId()) {
					it.remove();
					break;
				}
			}
		}
	}

	/**
	 * 获取需要删除的buff;
	 * 
	 * @return 不会返回null;
	 */
	private List<IBuff> getNeedRemoveBuffes() {
		List<IBuff> removeBuffes = new ArrayList<IBuff>();
		for (List<IBuff> buffs : allBuffs.values()) {
			for (IBuff buff : buffs) {
				if (buff.needRemove()) {
					removeBuffes.add(buff);
				}
			}
		}
		return removeBuffes;
	}

	/**
	 * 是否有指定的buff类型;
	 * 
	 * @param buffType
	 * @return
	 */
	public boolean hasBuff(BuffType buffType) {
		return this.allBuffs.get(buffType) != null && this.allBuffs.get(buffType).size() > 0;
	}

	/**
	 * 在战斗单元行动之前进行调用, 做一些比如每回合行动前先减血(中毒)之类的事情;
	 */
	public void beforeAction() {
		for (List<IBuff> buffs : allBuffs.values()) {
			for (IBuff buff : buffs) {
				buff.beforeAction();
			}
		}
	}
	
	@Override
	public void recordAction() {
		for (List<IBuff> buffs : allBuffs.values()) {
			for (IBuff buff : buffs) {
				buff.recordAction();
			}
		}
	}

	// 根据buff类型删除buff;
	public void removeBuffByType(ClearBuffType clearBuffType) {
		if (clearBuffType == ClearBuffType.CLEAR_ALL) {
			for (List<IBuff> buffs : allBuffs.values()) {
				for (IBuff buff : buffs) {
					buff.onRemove(false);
				}
			}
			allBuffs.clear();
			return;
		}
		if (clearBuffType == ClearBuffType.CLEAR_BUFF) {
			for (List<IBuff> buffs : this.allBuffs.values()) {
				Iterator<IBuff> it = buffs.iterator();
				while (it.hasNext()) {
					IBuff nextBuff = it.next();
					if (BuffSelfType.typeOf(nextBuff.getBuffTemplate()
							.getBuffSelfType()) == BuffSelfType.BUFF) {
						nextBuff.onRemove(false);
						it.remove();
					}
				}
			}
			return;
		}
		if (clearBuffType == ClearBuffType.CLEAR_DEBUFF) {
			for (List<IBuff> buffs : this.allBuffs.values()) {
				Iterator<IBuff> it = buffs.iterator();
				while (it.hasNext()) {
					IBuff nextBuff = it.next();
					if (BuffSelfType.typeOf(nextBuff.getBuffTemplate()
							.getBuffSelfType()) == BuffSelfType.DEBUFF) {
						nextBuff.onRemove(false);
						it.remove();
					}
				}
			}
			return;
		}
	}
}
