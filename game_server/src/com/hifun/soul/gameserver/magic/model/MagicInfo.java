package com.hifun.soul.gameserver.magic.model;

/**
 * 魔法信息;
 * 
 * @author crazyjohn
 * 
 */
public class MagicInfo {
	/** 魔法类型 */
	private int energyType;
	/** 初始值 */
	private int initValue;
	/** 最大值 */
	private int maxValue;
	/** 加成值 */
	private int addValue;

	public int getEnergyType() {
		return energyType;
	}

	public void setEnergyType(int energyType) {
		this.energyType = energyType;
	}

	public int getInitValue() {
		return initValue;
	}

	public void setInitValue(int initValue) {
		this.initValue = initValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getAddValue() {
		return addValue;
	}

	public void setAddValue(int addValue) {
		this.addValue = addValue;
	}
}
