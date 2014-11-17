package com.hifun.soul.gameserver.battle.gem;

import java.util.List;

/**
 * 魔法槽信息;
 * 
 * @author crazyjohn
 * 
 */
public class MagicSlotInfo {
	/** 当前大小 */
	private int currentSize;
	/** 容量 */
	private int capacity;
	/** 魔法类型 */
	private int energyType;
	/** 加成值 */
	private int addValue;

	public int getEnergyType() {
		return energyType;
	}

	public void setEnergyType(int energyType) {
		this.energyType = energyType;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * 此方法需要重构;
	 */
	public void putAll(List<GemPosition> gems) {
		boolean isTrigger = false;
		int addMagic = 0;
		for (GemPosition gem : gems) {
			if (gem.getType() != this.energyType) {
				continue;
			}
			addMagic++;
			isTrigger = true;
			// 检查魔法槽是否满了
			if (this.currentSize + addMagic >= this.capacity) {
				this.currentSize = this.capacity;
				return;
			}
		}
		if (isTrigger) {
			// 添加加成值;
			addMagic += this.addValue;
			// 检查魔法槽是否满了
			if (this.currentSize + addMagic >= this.capacity) {
				this.currentSize = this.capacity;
				return;
			}
		}
		// 加点
		setCurrentSize(this.currentSize + addMagic);
	}

	/**
	 * 增加能量值;
	 * 
	 * @param value
	 */
	public void addEnergy(int value) {
		// 检查魔法槽是否满了
		int result = value;
		if (this.currentSize + value >= this.capacity) {
			this.currentSize = this.capacity;
			return;
		}
		this.currentSize += result;
	}

	/**
	 * 减少能量值;
	 * 
	 * @param value
	 */
	public void reduceEnergy(int value) {
		if (this.currentSize - value < 0) {
			this.currentSize = 0;
			return;
		}
		this.currentSize -= value;
	}

	public int getAddValue() {
		return addValue;
	}

	public void setAddValue(int addValue) {
		this.addValue = addValue;
	}
}
