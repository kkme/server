package com.hifun.soul.gameserver.abattoir.msg;

/**
 * 角斗场仇人信息
 * 
 * @author yandajun
 * 
 */
public class AbattoirEnemyInfo {
	private long enemyId;
	private String enemyName;
	private int enemyLevel;
	private int lootTimeInterval;

	public long getEnemyId() {
		return enemyId;
	}

	public void setEnemyId(long enemyId) {
		this.enemyId = enemyId;
	}

	public String getEnemyName() {
		return enemyName;
	}

	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
	}

	public int getEnemyLevel() {
		return enemyLevel;
	}

	public void setEnemyLevel(int enemyLevel) {
		this.enemyLevel = enemyLevel;
	}

	public int getLootTimeInterval() {
		return lootTimeInterval;
	}

	public void setLootTimeInterval(int lootTimeInterval) {
		this.lootTimeInterval = lootTimeInterval;
	}

}
