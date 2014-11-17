package com.hifun.soul.gameserver.bloodtemple.msg;

/**
 * 嗜血神殿仇人信息
 * 
 * @author yandajun
 * 
 */
public class BloodTempleEnemyInfo {
	private long enemyId;
	private String enemyName;
	private int enemyLevel;
	private long lootTime;
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

	public long getLootTime() {
		return lootTime;
	}

	public void setLootTime(long lootTime) {
		this.lootTime = lootTime;
	}

	public int getLootTimeInterval() {
		return lootTimeInterval;
	}

	public void setLootTimeInterval(int lootTimeInterval) {
		this.lootTimeInterval = lootTimeInterval;
	}

}
