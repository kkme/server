package com.hifun.soul.gameserver.skill.template;

public class SkillDevelopInfo {
	private int skillDevelopType;
	private int icon;
	private int recommendPointType;
	private int mainCostMagic;
	private String assistCostMagic;
	
	public int getMainCostMagic() {
		return mainCostMagic;
	}
	public void setMainCostMagic(int mainCostMagic) {
		this.mainCostMagic = mainCostMagic;
	}
	public String getAssistCostMagic() {
		return assistCostMagic;
	}
	public void setAssistCostMagic(String assistCostMagic) {
		this.assistCostMagic = assistCostMagic;
	}
	public int getSkillDevelopType() {
		return skillDevelopType;
	}
	public void setSkillDevelopType(int skillDevelopType) {
		this.skillDevelopType = skillDevelopType;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getRecommendPointType() {
		return recommendPointType;
	}
	public void setRecommendPointType(int recommendPointType) {
		this.recommendPointType = recommendPointType;
	}
}
