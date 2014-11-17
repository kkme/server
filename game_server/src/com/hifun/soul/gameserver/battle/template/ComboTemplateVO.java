package com.hifun.soul.gameserver.battle.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * combo配置
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ComboTemplateVO extends TemplateObject {

	/**  伤害加成比率 */
	@ExcelCellBinding(offset = 1)
	protected int damageRate;

	/**  连击伤害加成值 */
	@ExcelCellBinding(offset = 2)
	protected int damage;


	public int getDamageRate() {
		return this.damageRate;
	}

	public void setDamageRate(int damageRate) {
		if (damageRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 伤害加成比率]damageRate的值不得小于0");
		}
		this.damageRate = damageRate;
	}
	
	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		if (damage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 连击伤害加成值]damage的值不得小于0");
		}
		this.damage = damage;
	}
	

	@Override
	public String toString() {
		return "ComboTemplateVO[damageRate=" + damageRate + ",damage=" + damage + ",]";

	}
}