package com.hifun.soul.gameserver.sprite.model;

/**
 * 精灵对酒操作信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpriteGuessOperationInfo {
	/** 功能是否开启 */
	private boolean open;
	/** 消耗货币类型 */
	private int costType;
	/** 消耗货币数量 */
	private int costNum;
	/** 功能开启等级 */
	private int openLevel;
	/** 功能开启vip等级 */
	private int openVipLevel;

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getCostType() {
		return costType;
	}

	public void setCostType(int costType) {
		this.costType = costType;
	}

	public int getCostNum() {
		return costNum;
	}

	public void setCostNum(int costNum) {
		this.costNum = costNum;
	}

	public int getOpenLevel() {
		return openLevel;
	}

	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}

	public int getOpenVipLevel() {
		return openVipLevel;
	}

	public void setOpenVipLevel(int openVipLevel) {
		this.openVipLevel = openVipLevel;
	}

}
