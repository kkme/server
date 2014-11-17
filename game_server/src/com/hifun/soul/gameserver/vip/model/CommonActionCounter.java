package com.hifun.soul.gameserver.vip.model;

/**
 * 操作计数器
 * 
 * @author magicstone
 *
 */
public class CommonActionCounter {
	/** 总共的操作次数 */
	private int totalCount;
	/** 剩余次数次数 */
	private int remainCount;
	/** 需要的货币类型 */
	private int currencyType;
	/** 需要的货币数量*/
	private int currencyNum;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public int getCurrencyNum() {
		return currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		this.currencyNum = currencyNum;
	}

}
