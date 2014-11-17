package com.hifun.soul.gameserver.foster;

public enum FosterModeType {
	NORMAL_FOSTER(1),
	SUPER_FOSTER(2);
	private int index;
	private FosterModeType(int index){
		this.index = index;
	}
	public int getIndex(){
		return index;
	}
}
