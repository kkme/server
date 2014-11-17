package com.hifun.soul.gameserver.battle.property;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.role.properties.IntPropertySet;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;

/**
 * 战斗属性;
 * 
 * @author crazyjohn
 * 
 */
public class BattleProperty implements IRecalculateable {
	/** 参战的二级属性 */
	private IntPropertySet initLevel2Properties = new Level2Property();
	/** add */
	private IntPropertySet addLevel2Properties = new Level2Property();
	/** multiply */
	private IntPropertySet multiplyLevel2Properties = new Level2Property();
	/** final */
	private IntPropertySet finalLevel2Properties = new Level2Property();
	/** 等级 */
	private int level;
	/** 黑宝石攻击加成;效果持续到战斗结束 */
	private int blackGemAttackAddRate;

	/** 是否必定触发暴击 */
	// private boolean mustCrit = false;

	/** 当前的HP */
	// private int hp;

	public int getHp() {
		return this.finalLevel2Properties
				.getPropertyValue(Level2Property.MAX_HP);
	}

	public void setHp(int hp) {
		if (hp < 0) {
			hp = 0;
		}
		this.finalLevel2Properties.setPropertyValue(Level2Property.MAX_HP, hp);
	}

	/**
	 * 根据索引获取战斗属性;
	 * 
	 * @param index
	 * @return
	 */
	public int getBattleFinalPropertyByIndex(int index) {
		return this.finalLevel2Properties.getPropertyValue(index);
	}

	/**
	 * 设置战斗初始属性;
	 * <p>
	 * 所有战斗属性都是从玩家的二级属性中复制而来;
	 * 
	 * @param index
	 * @param value
	 */
	public void setBattleInitPropertyByIndex(int index, int value) {
		this.initLevel2Properties.setPropertyValue(index, value);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public void recalculate() {
		for (int index = Level2Property._BEGIN; index <= Level2Property._END; index++) {
			finalLevel2Properties
					.setPropertyValue(
							index,
							(int) (Math.floor((this.initLevel2Properties
									.getPropertyValue(index) + this.addLevel2Properties
									.getPropertyValue(index))
									* (SharedConstants.PROPERTY_DIVISOR + this.multiplyLevel2Properties
											.getPropertyValue(index))
									/ SharedConstants.PROPERTY_DIVISOR)));
		}
	}

	/**
	 * 属性加成接口;
	 * 
	 * @param amendType
	 * @param propId
	 * @param value
	 */
	public void amendProperty(AmendMethod amendType, int propId, int value) {
		// 属性类型,限定为只有二级属性
		int properType = PropertyType.propertyKeyToPropertyType(propId);
		if (properType != PropertyType.LEVEL2_PROPERTY) {
			throw new IllegalArgumentException();
		}
		int index = PropertyType.propertyKeyToPropertyIndex(propId);
		// 加法
		if (amendType == AmendMethod.ADD) {
			this.addLevel2Properties.setPropertyValue(index, value
					+ this.addLevel2Properties.getPropertyValue(index));
		}
		// 乘法
		if (amendType == AmendMethod.MULIPLY) {
			this.multiplyLevel2Properties.setPropertyValue(index, value
					+ this.multiplyLevel2Properties.getPropertyValue(index));
		}
		// 重新计算指定属性
		this.recalculate(index, value);
	}

	/**
	 * 重新计算指定的属性;<br>
	 * 解決了角色上buff以後, 导致属性重新计算, 角色血量回升无法死亡的bug;
	 * 
	 * @param propIndex
	 * @param value
	 */
	private void recalculate(int propIndex, int value) {
		boolean done = false;
		for (int index = Level2Property._BEGIN; index <= Level2Property._END; index++) {
			if (done) {
				break;
			}
			if (index != propIndex) {
				continue;
			}
			finalLevel2Properties
					.setPropertyValue(
							index,
							(int) (Math.floor((this.initLevel2Properties
									.getPropertyValue(index) + this.addLevel2Properties
									.getPropertyValue(index))
									* (SharedConstants.PROPERTY_DIVISOR + this.multiplyLevel2Properties
											.getPropertyValue(index))
									/ SharedConstants.PROPERTY_DIVISOR)));
			done = true;
		}
	}

	/**
	 * 获取先攻值;
	 * 
	 * @return
	 */
	public int getFirstAttack() {
		return this.finalLevel2Properties
				.getPropertyValue(Level2Property.FIRST_ATTACK);

	}

	/**
	 * 触发攻击加成;
	 */
	public void triggerAttackAdd(int counter) {
		blackGemAttackAddRate += this.finalLevel2Properties
				.getPropertyValue(Level2Property.BLACK_GEM_ATTACK_PER_ADD_RATE)
				* counter;
		if (blackGemAttackAddRate >= GameServerAssist.getGameConstants().getBlackMaxAddRate()) {
			this.blackGemAttackAddRate = GameServerAssist.getGameConstants().getBlackMaxAddRate();
		}
	}

	/**
	 * 获取当前攻击加成;
	 * 
	 * @return
	 */
	public int getAttackAddedRate() {
		return blackGemAttackAddRate;
	}

	// public void mustCrit() {
	// this.mustCrit = true;
	// }
	//
	// public void resetMustCrit() {
	// this.mustCrit = false;
	// }
	//
	// public boolean isMustCrit() {
	// return this.mustCrit;
	// }
}
