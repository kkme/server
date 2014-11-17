package com.hifun.soul.gameserver.warrior;

/**
 * 完成勇士任务所获得的奖励
 * 
 * @author magicstone
 *
 */
public class WarriorQuestReward {
	private int questId;
	private int coin;
	private int exp;
	private int techPoint;
	private int state;
	public int getQuestId() {
		return questId;
	}
	public void setQuestId(int questId) {
		this.questId = questId;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getTechPoint() {
		return techPoint;
	}
	public void setTechPoint(int techPoint) {
		this.techPoint = techPoint;
	}
	
}
