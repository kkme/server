package com.hifun.soul.common.model.array;

/**
 * 阵形面板显示的武将信息
 * @author SevenSoul
 *
 */
public class ArrayPetInfo {

	/** 武将uuid */
	private long uuid;
	/** 头像 */
	private int photo;
	/** 名称 */
	private String name;
	/** 等级 */
	private int level;
	/** 士兵等级 */
	private int soldierLevel;
	/** 兵种 */
	private String soldier;
	/** 兵种描述 */
	private String soldierDesc;
	/** 战法 */
	private String skill;
	/** 战法描述 */
	private String skillDesc;
	/** 所在阵中位置*/
	private int arrayIndex;
	
	public long getUuid() {
		return uuid;
	}
	
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}
	
	public int getPhoto() {
		return photo;
	}
	
	public void setPhoto(int photo) {
		this.photo = photo;
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
	
	public int getSoldierLevel() {
		return soldierLevel;
	}
	
	public void setSoldierLevel(int soldierLevel) {
		this.soldierLevel = soldierLevel;
	}
	
	public String getSoldier() {
		return soldier;
	}
	
	public void setSoldier(String soldier) {
		this.soldier = soldier;
	}
	
	public String getSkill() {
		return skill;
	}
	
	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public int getArrayIndex() {
		return arrayIndex;
	}
	
	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}
	
	public String getSkillDesc() {
		return skillDesc;
	}
	
	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
	}
	
	public String getSoldierDesc() {
		return soldierDesc;
	}
	
	public void setSoldierDesc(String soldierDesc) {
		this.soldierDesc = soldierDesc;
	}
}
