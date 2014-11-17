package com.hifun.soul.gameserver.skill.slot;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.template.SkillSlotTemplate;

/**
 * 技能栏位;
 * @author crazyjohn
 *
 */
public class SkillSlot {
	/** 栏位索引 */
	private int slotIndex;
	/** 栏位模版 */
	private SkillSlotTemplate template;
	private int state;
	/** 是否装备了技能 */
	private boolean equipedSkill;
	private ISkill skill;
	
	protected int needHumanLevel;
	protected int costCoin;
	protected int costCrystal;
	private int openTitle;
	private String openTitleName;
	
	public int getNeedHumanLevel() {
		return this.template.getHumanLevel();
	}
	public void setNeedHumanLevel(int needHumanLevel) {
		this.needHumanLevel = needHumanLevel;
	}
	public int getCostCoin() {
		return this.template.getCostCoin();
	}
	public void setCostCoin(int costCoin) {
		this.costCoin = costCoin;
	}
	public int getCostCrystal() {
		return this.template.getCostCrystal();
	}
	public void setCostCrystal(int costCrystal) {
		this.costCrystal = costCrystal;
	}
	public int getSlotIndex() {
		return slotIndex;
	}
	public void setSlotIndex(int slotIndex) {
		this.slotIndex = slotIndex;
	}
	public SkillSlotTemplate getTemplate() {
		return template;
	}
	public void setTemplate(SkillSlotTemplate template) {
		this.template = template;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isEquipedSkill() {
		return equipedSkill;
	}
	public void setEquipedSkill(boolean equipedSkill) {
		this.equipedSkill = equipedSkill;
	}
	public boolean isOpen() {
		return this.state == SkillSlotState.OPEN.getIndex();
	}
	public void equipSkill(ISkill skill) {
		this.setEquipedSkill(true);
		this.setSkill(skill);
		skill.setSlotIndex(this.template.getId());
	}
	public ISkill getSkill() {
		return skill;
	}
	public void setSkill(ISkill skill) {
		this.skill = skill;
	}
	
	public void unEquipSkill(ISkill skill) {
		this.skill = null;
		this.setEquipedSkill(false);
		skill.setSlotIndex(SharedConstants.INVALID_SKILL_SLOT_INDEX);
	}
	
	public int getOpenTitle() {
		return openTitle;
	}
	public void setOpenTitle(int openTitle) {
		this.openTitle = openTitle;
	}
	public String getOpenTitleName() {
		return openTitleName;
	}
	public void setOpenTitleName(String openTitleName) {
		this.openTitleName = openTitleName;
	}
	
}
