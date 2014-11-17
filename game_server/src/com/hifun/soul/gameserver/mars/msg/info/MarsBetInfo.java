package com.hifun.soul.gameserver.mars.msg.info;

/**
 * 战神之巅下注信息
 * 
 * @author yandajun
 * 
 */
public class MarsBetInfo {
	/** 倍数 */
	private int multiple;
	/** 花费数量 */
	private int costNum;
	/** 是否可见 */
	private boolean visible;

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getCostNum() {
		return costNum;
	}

	public void setCostNum(int costNum) {
		this.costNum = costNum;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
