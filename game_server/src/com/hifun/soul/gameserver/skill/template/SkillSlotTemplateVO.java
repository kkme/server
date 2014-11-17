package com.hifun.soul.gameserver.skill.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 技能栏位
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SkillSlotTemplateVO extends TemplateObject {

	/**  解锁角色等级 */
	@ExcelCellBinding(offset = 1)
	protected int humanLevel;

	/**  解锁花费金币 */
	@ExcelCellBinding(offset = 2)
	protected int costCoin;

	/**  直接解锁花费魔晶 */
	@ExcelCellBinding(offset = 3)
	protected int costCrystal;


	public int getHumanLevel() {
		return this.humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		if (humanLevel < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 解锁角色等级]humanLevel的值不得小于1");
		}
		this.humanLevel = humanLevel;
	}
	
	public int getCostCoin() {
		return this.costCoin;
	}

	public void setCostCoin(int costCoin) {
		if (costCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 解锁花费金币]costCoin的值不得小于0");
		}
		this.costCoin = costCoin;
	}
	
	public int getCostCrystal() {
		return this.costCrystal;
	}

	public void setCostCrystal(int costCrystal) {
		if (costCrystal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 直接解锁花费魔晶]costCrystal的值不得小于0");
		}
		this.costCrystal = costCrystal;
	}
	

	@Override
	public String toString() {
		return "SkillSlotTemplateVO[humanLevel=" + humanLevel + ",costCoin=" + costCoin + ",costCrystal=" + costCrystal + ",]";

	}
}