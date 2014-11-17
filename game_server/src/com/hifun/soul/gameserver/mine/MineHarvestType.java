package com.hifun.soul.gameserver.mine;

/**
 * 精炼厂收获类型
 * 
 * @author yandajun
 * 
 */
public enum MineHarvestType {
	/** 金币 */
	COIN(1),
	/** 培养币 */
	TRAIN_COIN(2),
	/** 物品 */
	ITEM(3);
	private int index;

	MineHarvestType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
