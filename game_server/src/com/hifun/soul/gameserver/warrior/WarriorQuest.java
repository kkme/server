package com.hifun.soul.gameserver.warrior;

/**
 * 勇士任务
 * @author magicstone
 *
 */
public class WarriorQuest {
	private int id;
	private int totalCount;
	private int completeCount;
	private int damageHpPercent;
	private int questState;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCount(int completeCount) {
		this.completeCount = completeCount;
	}
	public int getDamageHpPercent() {
		return damageHpPercent;
	}
	public void setDamageHpPercent(int damageHpPercent) {
		this.damageHpPercent = damageHpPercent;
	}
	public int getQuestState() {
		return questState;
	}
	public void setQuestState(int questState) {
		this.questState = questState;
	}
	
}
