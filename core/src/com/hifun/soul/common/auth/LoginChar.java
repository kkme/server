package com.hifun.soul.common.auth;

/**
 * 登录选角色时候服务器发给客户端的角色信息;
 * 
 * @author crazyjohn
 * 
 */
public class LoginChar {
	/** 角色GUID */
	private long humanGuid;
	/** 角色名称 */
	private String name;
	/** 角色职业 */
	private int occupation;
	/** 角色等级 */
	private int level;
	/** 角色精力值 */
	private long energy;
	/** 角色主城等级 */
	private int homeLevel;
	/** 角色当前金币数 */
	private long coins;

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHomeLevel() {
		return homeLevel;
	}

	public void setHomeLevel(int homeLevel) {
		this.homeLevel = homeLevel;
	}

	public long getCoins() {
		return coins;
	}

	public void setCoins(long coins) {
		this.coins = coins;
	}

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getEnergy() {
		return energy;
	}

	public void setEnergy(long energy) {
		this.energy = energy;
	}
}
