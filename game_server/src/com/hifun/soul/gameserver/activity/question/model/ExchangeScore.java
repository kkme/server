package com.hifun.soul.gameserver.activity.question.model;

public class ExchangeScore {
	/**	兑换id */
	private int id;
	/**	积分线 */
	private int score;
	/**	兑换状态 */
	private int exchangeState;
	/** 金币 */
	private int coin;
	/** 经验 */
	private int exp;
	/** 科技点 */
	private int techPoint;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getExchangeState() {
		return exchangeState;
	}
	public void setExchangeState(int exchangeState) {
		this.exchangeState = exchangeState;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getTechPoint() {
		return techPoint;
	}
	public void setTechPoint(int techPoint) {
		this.techPoint = techPoint;
	}
}
