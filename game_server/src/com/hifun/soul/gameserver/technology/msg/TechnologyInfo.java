package com.hifun.soul.gameserver.technology.msg;

/**
 * 
 * 科技基础信息
 * 
 * @author magicstone
 *
 */
public class TechnologyInfo {

	/** 科技id */
	private int technologyId;
	/** 科技名称 */
	private String name;
	/** 科技等级 */
	private int level;
	/** 科技图标 */
	private int icon;
	/** 是否达到满级 */
	private boolean maxLevelReached;
	/** 升级消耗的科技点数 */
	private int costTechPointNum;
	/** 角色等级限制 */
	private int roleLevel;
	
	public int getTechnologyId() {
		return technologyId;
	}
	public void setTechnologyId(int technologyId) {
		this.technologyId = technologyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public boolean getMaxLevelReached() {
		return maxLevelReached;
	}
	public void setMaxLevelReached(boolean maxLevelReached) {
		this.maxLevelReached = maxLevelReached;
	}	
	public int getCostTechPointNum() {
		return costTechPointNum;
	}
	public void setCostTechPointNum(int costTechPointNum) {
		this.costTechPointNum = costTechPointNum;
	}
	public int getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}	
	
}
