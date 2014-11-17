package com.hifun.soul.gameserver.human.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 角色升级模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HumanLevelUpTemplateVO extends TemplateObject {

	/**  升级所需经验 */
	@ExcelCellBinding(offset = 1)
	protected int experience;

	/** 升级下发的一级属性自由点 */
	@ExcelCellBinding(offset = 2)
	protected int level1propCount;

	/**  处于该等级的初始hp */
	@ExcelCellBinding(offset = 3)
	protected int hp;

	/** 宝石产量 */
	@ExcelCellBinding(offset = 4)
	protected int harvestGemNum;

	/** 税收产量 */
	@ExcelCellBinding(offset = 5)
	protected int levyRevenue;

	/** 技能属性点 */
	@ExcelCellBinding(offset = 6)
	protected int skillPoint;


	public int getExperience() {
		return this.experience;
	}

	public void setExperience(int experience) {
		if (experience == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 升级所需经验]experience不可以为0");
		}
		if (experience < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 升级所需经验]experience的值不得小于0");
		}
		this.experience = experience;
	}
	
	public int getLevel1propCount() {
		return this.level1propCount;
	}

	public void setLevel1propCount(int level1propCount) {
		if (level1propCount < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[升级下发的一级属性自由点]level1propCount的值不得小于0");
		}
		this.level1propCount = level1propCount;
	}
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		if (hp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 处于该等级的初始hp]hp的值不得小于0");
		}
		this.hp = hp;
	}
	
	public int getHarvestGemNum() {
		return this.harvestGemNum;
	}

	public void setHarvestGemNum(int harvestGemNum) {
		if (harvestGemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[宝石产量]harvestGemNum不可以为0");
		}
		this.harvestGemNum = harvestGemNum;
	}
	
	public int getLevyRevenue() {
		return this.levyRevenue;
	}

	public void setLevyRevenue(int levyRevenue) {
		if (levyRevenue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[税收产量]levyRevenue不可以为0");
		}
		this.levyRevenue = levyRevenue;
	}
	
	public int getSkillPoint() {
		return this.skillPoint;
	}

	public void setSkillPoint(int skillPoint) {
		if (skillPoint == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[技能属性点]skillPoint不可以为0");
		}
		this.skillPoint = skillPoint;
	}
	

	@Override
	public String toString() {
		return "HumanLevelUpTemplateVO[experience=" + experience + ",level1propCount=" + level1propCount + ",hp=" + hp + ",harvestGemNum=" + harvestGemNum + ",levyRevenue=" + levyRevenue + ",skillPoint=" + skillPoint + ",]";

	}
}