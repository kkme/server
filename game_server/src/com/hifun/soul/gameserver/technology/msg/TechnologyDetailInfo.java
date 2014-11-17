package com.hifun.soul.gameserver.technology.msg;


/**
 * 
 * 科技详细信息
 * 
 * @author magicstone
 *
 */
public class TechnologyDetailInfo {

	/** 科技id */
	private int technologyId;
	/** 科技名称 */
	private String name;
	/** 科技等级 */
	private int level;
	/** 科技图标 */
	private int icon;
	/** 每级增加值 */
	private int propAddValue;
	/** 属性名名称 */
	private String propName;
	/** 升级消耗的科技点数 */
	private int costValue;
	/** 需要人物等级 */
	private int roleLevel;
	/** 下一级科技增加值 */
	private int nextPropAddValue;
	
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
	public int getPropAddValue() {
		return propAddValue;
	}
	public void setPropAddValue(int propAddValue) {
		this.propAddValue = propAddValue;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public int getCostValue() {
		return costValue;
	}
	public void setCostValue(int costValue) {
		this.costValue = costValue;
	}
	public int getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}
	public int getNextPropAddValue() {
		return nextPropAddValue;
	}
	public void setNextPropAddValue(int nextPropAddValue) {
		this.nextPropAddValue = nextPropAddValue;
	}
	
}
