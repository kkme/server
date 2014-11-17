package com.hifun.soul.gameserver.mine;

import com.hifun.soul.proto.common.Mine.MineField;

/**
 * 矿场
 * @author magicstone
 *
 */
public class MineFieldModel {
	private int index;
	private int type;
	
	public MineFieldModel(){
		
	}
	public MineFieldModel(int index,int type){
		this.index = index;
		this.type = type;
	}
	
	public MineFieldModel(MineField src){
		this(src.getIndex(),src.getType());
	}
	
	public MineField convertToMineField(){
		MineField.Builder builder = MineField.newBuilder();
		builder.setIndex(index);
		builder.setType(type);
		return builder.build();
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
