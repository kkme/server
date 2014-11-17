package com.hifun.soul.gameserver.battle;

import com.hifun.soul.gameserver.battle.gem.GemType;

/**
 * 魔法槽;
 * 
 * @author crazyjohn
 * 
 */
public class MagicSlot implements IMagicSlot {
	/** 容量,根据相应的宝石属性初始化 */
	private int capacity;
	/** 当前的容量 */
	private int currentSize;
	/** 宝石类型 */
	private GemType type;

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public void reset() {
		this.currentSize = 0;
	}

	@Override
	public int getCurrentSize() {
		return currentSize;
	}

	@Override
	public GemType getType() {
		return type;
	}

}
