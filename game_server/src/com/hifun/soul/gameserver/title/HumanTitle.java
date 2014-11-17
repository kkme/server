package com.hifun.soul.gameserver.title;

import com.hifun.soul.gameserver.title.template.HumanTitleProperty;

/**
 * 军衔
 * 
 * @author yandajun
 * 
 */
public class HumanTitle {
	/** 军衔id */
	private int titleId;
	/** 军衔名称 */
	private String titleName;
	/** 升级所需威望 */
	private int needPrestige;
	/** 荣誉上限 */
	private int maxHonor;
	/** 携带技能数量 */
	private int titleSkillNum;
	/** 每日俸禄 */
	private int titleSalary;
	/** 获得的属性加成 */
	private HumanTitleProperty[] titleProperties;

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public int getNeedPrestige() {
		return needPrestige;
	}

	public void setNeedPrestige(int needPrestige) {
		this.needPrestige = needPrestige;
	}

	public int getMaxHonor() {
		return maxHonor;
	}

	public void setMaxHonor(int maxHonor) {
		this.maxHonor = maxHonor;
	}

	public int getTitleSkillNum() {
		return titleSkillNum;
	}

	public void setTitleSkillNum(int titleSkillNum) {
		this.titleSkillNum = titleSkillNum;
	}

	public int getTitleSalary() {
		return titleSalary;
	}

	public void setTitleSalary(int titleSalary) {
		this.titleSalary = titleSalary;
	}

	public HumanTitleProperty[] getTitleProperties() {
		return titleProperties;
	}

	public void setTitleProperties(HumanTitleProperty[] titleProperties) {
		this.titleProperties = titleProperties;
	}

}
