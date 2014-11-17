package com.hifun.soul.gameserver.skill.template;

public class SkillInfo {
	/** 技能ID */
	private int skillId;
	/** 技能名称 */
	private String skillName;
	/** 技能描述 */
	private String skillDesc;
	/** 技能冷却回合 */
	private int cooldownRound;
	/** 攻击类型,近身或者远程 */
	private int attackType;
	/** 技能类型(普通攻击,combo攻击, 其它技能) */
	private int skillType;
	/** 技能动作id */
	private int skillActionId;
	/** 飞行特效ID */
	private int flyEffectId;
	/** 受击特效ID */
	private int beHitedEffectId;
	/** 敌方受击特效ID */
	private int enemyBeHitedEffectId;
	/** 是否需要点选宝石 */
	private boolean needSelectGem;
	/** 点选的范围类型 */
	private int rangeType;
	/** 使用后回合是否结束 */
	private boolean useRoundOver;
	// 各种宝石消耗
	private int redCost;
	private int yellowCost;
	private int greenCost;
	private int blueCost;
	private int purpleCost;
	/** 技能音效 */
	private int skillSound;
	/** 技能图标ID */
	private int skillIcon;
	/** 是否在战斗中携带 */
	private boolean isCarried;
	private int slotIndex;
	/** 技能的状态 */
	private int skillState;
	/** 技能的发展种类 */
	private int skillDevelopType;
	/** 需要等级 */
	private int needLevel;
	/** 需要技能点 */
	private int needSkillPoints;
	/** 卷轴名称 */
	private String skillScrollName;
	/** 是否需要卷轴 */
	private boolean isNeedSkillScroll;
	/** 前置技能卷轴名称 */
	private String preSkillName;
	/** 前置技能是否已经开启 */
	private boolean preSkillIsOpen = false;
	/** 是否拥有技能卷轴 */
	private boolean hasSkillScroll = false;
	/** 技能点是否足够 */
	private boolean skillPointsIsEnough = false;
	

	public int getSkillActionId() {
		return skillActionId;
	}

	public void setSkillActionId(int skillActionId) {
		this.skillActionId = skillActionId;
	}

	public int getFlyEffectId() {
		return flyEffectId;
	}

	public void setFlyEffectId(int flyEffectId) {
		this.flyEffectId = flyEffectId;
	}

	public int getBeHitedEffectId() {
		return beHitedEffectId;
	}

	public void setBeHitedEffectId(int beHitedEffectId) {
		this.beHitedEffectId = beHitedEffectId;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillDesc() {
		return skillDesc;
	}

	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
	}

	public int getCooldownRound() {
		return cooldownRound;
	}

	public void setCooldownRound(int cooldownRound) {
		this.cooldownRound = cooldownRound;
	}

	public int getRedCost() {
		return redCost;
	}

	public void setRedCost(int redCost) {
		this.redCost = redCost;
	}

	public int getYellowCost() {
		return yellowCost;
	}

	public void setYellowCost(int yellowCost) {
		this.yellowCost = yellowCost;
	}

	public int getGreenCost() {
		return greenCost;
	}

	public void setGreenCost(int greenCost) {
		this.greenCost = greenCost;
	}

	public int getBlueCost() {
		return blueCost;
	}

	public void setBlueCost(int blueCost) {
		this.blueCost = blueCost;
	}

	public int getPurpleCost() {
		return purpleCost;
	}

	public void setPurpleCost(int purpleCost) {
		this.purpleCost = purpleCost;
	}

	public int getAttackType() {
		return attackType;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	public int getSkillType() {
		return skillType;
	}

	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}

	public boolean getNeedSelectGem() {
		return needSelectGem;
	}

	public void setNeedSelectGem(boolean needSelectGem) {
		this.needSelectGem = needSelectGem;
	}

	public boolean getIsCarried() {
		return isCarried;
	}

	public void setIsCarried(boolean isCarried) {
		this.isCarried = isCarried;
	}

	public int getSkillIcon() {
		return skillIcon;
	}

	public void setSkillIcon(int skillIcon) {
		this.skillIcon = skillIcon;
	}

	public int getSlotIndex() {
		return slotIndex;
	}

	public void setSlotIndex(int slotIndex) {
		this.slotIndex = slotIndex;
	}

	public int getEnemyBeHitedEffectId() {
		return enemyBeHitedEffectId;
	}

	public void setEnemyBeHitedEffectId(int enemyBeHitedEffectId) {
		this.enemyBeHitedEffectId = enemyBeHitedEffectId;
	}

	public int getSkillState() {
		return skillState;
	}

	public void setSkillState(int skillState) {
		this.skillState = skillState;
	}

	public int getSkillDevelopType() {
		return skillDevelopType;
	}

	public void setSkillDevelopType(int skillDevelopType) {
		this.skillDevelopType = skillDevelopType;
	}

	public int getNeedLevel() {
		return needLevel;
	}

	public void setNeedLevel(int needLevel) {
		this.needLevel = needLevel;
	}

	public int getNeedSkillPoints() {
		return needSkillPoints;
	}

	public void setNeedSkillPoints(int needSkillPoints) {
		this.needSkillPoints = needSkillPoints;
	}

	public String getSkillScrollName() {
		return skillScrollName;
	}

	public void setSkillScrollName(String skillScrollName) {
		this.skillScrollName = skillScrollName;
	}

	public boolean getIsNeedSkillScroll() {
		return isNeedSkillScroll;
	}

	public void setIsNeedSkillScroll(boolean isNeedSkillScroll) {
		this.isNeedSkillScroll = isNeedSkillScroll;
	}

	public String getPreSkillName() {
		return preSkillName;
	}

	public void setPreSkillName(String preSkillName) {
		this.preSkillName = preSkillName;
	}

	public int getSkillSound() {
		return skillSound;
	}

	public void setSkillSound(int skillSound) {
		this.skillSound = skillSound;
	}

	public boolean isUseRoundOver() {
		return useRoundOver;
	}

	public void setUseRoundOver(boolean useRoundOver) {
		this.useRoundOver = useRoundOver;
	}

	public boolean getUseRoundOver() {
		return isUseRoundOver();
	}

	public int getRangeType() {
		return rangeType;
	}

	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}

	public boolean getPreSkillIsOpen() {
		return preSkillIsOpen;
	}

	public void setPreSkillIsOpen(boolean preSkillIsOpen) {
		this.preSkillIsOpen = preSkillIsOpen;
	}

	public boolean getHasSkillScroll() {
		return hasSkillScroll;
	}

	public void setHasSkillScroll(boolean hasSkillScroll) {
		this.hasSkillScroll = hasSkillScroll;
	}

	public boolean getSkillPointsIsEnough() {
		return skillPointsIsEnough;
	}

	public void setSkillPointsIsEnough(boolean skillPointsIsEnough) {
		this.skillPointsIsEnough = skillPointsIsEnough;
	}
	
}
