package com.hifun.soul.gameserver.training;

import com.hifun.soul.core.enums.IndexedEnum;

public enum TrainingType implements IndexedEnum {
	/** 普通训练 */
	NORMAL_TRAINING(1),
	/** VIP训练 */
	VIP_TRAINING(2);
	
	private int index;
	
	private TrainingType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
}
