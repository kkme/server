package com.hifun.soul.gameserver.escort;

/**
 * 押运日志
 * 
 * @author yandajun
 * 
 */
public class EscortLog {
	private long id;
	private long firstId;
	private String firstName;
	private long secondId;
	private String secondName;
	private String monserName;
	private int robCoin;

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

	public long getSecondId() {
		return secondId;
	}

	public void setSecondId(long secondId) {
		this.secondId = secondId;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getMonserName() {
		return monserName;
	}

	public void setMonserName(String monserName) {
		this.monserName = monserName;
	}

	public int getRobCoin() {
		return robCoin;
	}

	public void setRobCoin(int robCoin) {
		this.robCoin = robCoin;
	}
}
