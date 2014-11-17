package com.hifun.soul.gameserver.human.quest;

/**
 * 任务目标数据;
 * 
 * @author crazyjohn
 * 
 */
public class QuestAimInfo {
	/** 目标类型 */
	private int aimType;
	/** 索引 */
	private int aimIndex;
	/** 目标值 */
	private int aimValue;
	/** 当前值 */
	private int currentValue;
	/** 目标描述 */
	private String desc;

	public int getAimType() {
		return aimType;
	}

	public void setAimType(int aimType) {
		this.aimType = aimType;
	}

	public int getAimIndex() {
		return aimIndex;
	}

	public void setAimIndex(int aimIndex) {
		this.aimIndex = aimIndex;
	}

	public int getAimValue() {
		return aimValue;
	}

	public void setAimValue(int aimValue) {
		this.aimValue = aimValue;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
