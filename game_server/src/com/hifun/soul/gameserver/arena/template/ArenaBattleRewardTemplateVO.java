package com.hifun.soul.gameserver.arena.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 竞技场战斗奖励配置模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ArenaBattleRewardTemplateVO extends TemplateObject {

	/** 等级区间(上) */
	@ExcelCellBinding(offset = 1)
	protected int minLevel;

	/** 等级区间(下) */
	@ExcelCellBinding(offset = 2)
	protected int maxLevel;

	/** 金钱 */
	@ExcelCellBinding(offset = 3)
	protected int money;

	/** 经验 */
	@ExcelCellBinding(offset = 4)
	protected int experience;

	/** 荣誉 */
	@ExcelCellBinding(offset = 5)
	protected int honour;


	public int getMinLevel() {
		return this.minLevel;
	}

	public void setMinLevel(int minLevel) {
		if (minLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[等级区间(上)]minLevel不可以为0");
		}
		this.minLevel = minLevel;
	}
	
	public int getMaxLevel() {
		return this.maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		if (maxLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[等级区间(下)]maxLevel不可以为0");
		}
		this.maxLevel = maxLevel;
	}
	
	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		if (money < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[金钱]money的值不得小于0");
		}
		this.money = money;
	}
	
	public int getExperience() {
		return this.experience;
	}

	public void setExperience(int experience) {
		if (experience < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[经验]experience的值不得小于0");
		}
		this.experience = experience;
	}
	
	public int getHonour() {
		return this.honour;
	}

	public void setHonour(int honour) {
		if (honour < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[荣誉]honour的值不得小于0");
		}
		this.honour = honour;
	}
	

	@Override
	public String toString() {
		return "ArenaBattleRewardTemplateVO[minLevel=" + minLevel + ",maxLevel=" + maxLevel + ",money=" + money + ",experience=" + experience + ",honour=" + honour + ",]";

	}
}