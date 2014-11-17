package com.hifun.soul.gameserver.abattoir;

public class AbattoirLog {
	private long id;

	private long firstId;

	private String firstName;

	private int firstLevel;

	private long secondId;

	private int result;

	private long lootTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFirstId() {
		return firstId;
	}

	public void setFirstId(long firstId) {
		this.firstId = firstId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getFirstLevel() {
		return firstLevel;
	}

	public void setFirstLevel(int firstLevel) {
		this.firstLevel = firstLevel;
	}

	public long getSecondId() {
		return secondId;
	}

	public void setSecondId(long secondId) {
		this.secondId = secondId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getLootTime() {
		return lootTime;
	}

	public void setLootTime(long lootTime) {
		this.lootTime = lootTime;
	}

}
