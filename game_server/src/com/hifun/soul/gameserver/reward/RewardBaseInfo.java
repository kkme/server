package com.hifun.soul.gameserver.reward;
/**
 * 奖励基础信息
 * @author magicstone
 *
 */
public class RewardBaseInfo {
	/** 奖励id */
	private int id;
	/** 奖励名称 */
	private String name;
	/** 奖励图标 */
	private int icon;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
}
