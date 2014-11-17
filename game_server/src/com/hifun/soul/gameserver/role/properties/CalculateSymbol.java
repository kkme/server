package com.hifun.soul.gameserver.role.properties;

/**
 * 计算符号;<br>
 * 
 * @author crazyjohn
 * 
 */
public enum CalculateSymbol {
	/** 正 */
	POSITIVE(1),
	/** 负 */
	NEGATIVE(-1);
	private int value;

	CalculateSymbol(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
