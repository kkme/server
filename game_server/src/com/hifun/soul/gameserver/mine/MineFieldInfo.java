package com.hifun.soul.gameserver.mine;

/**
 * 矿坑
 * 
 * @author magicstone
 * 
 */
public class MineFieldInfo {
	/** 位置索引 */
	private int index;
	/** 矿坑类型 */
	private int type;
	/** 矿坑名称 */
	private String name;
	/** 描述 */
	private String desc;
	/** 图片id */
	private int picId;
	/** 是否为贫矿 */
	private boolean isBadMineField;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getPicId() {
		return picId;
	}
	public void setPicId(int picId) {
		this.picId = picId;
	}
	public boolean getIsBadMineField() {
		return isBadMineField;
	}
	public void setIsBadMineField(boolean isBadMineField) {
		this.isBadMineField = isBadMineField;
	}
	
}
