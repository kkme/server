package com.hifun.soul.gameserver.legion;

public class LegionHonor {
	private long id;
	private long legionId;
	private int titleId;
	private int exchangeNum;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public int getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(int exchangeNum) {
		this.exchangeNum = exchangeNum;
	}
}
