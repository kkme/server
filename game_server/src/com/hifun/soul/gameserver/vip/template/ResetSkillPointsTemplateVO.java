package com.hifun.soul.gameserver.vip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 重置技能点模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ResetSkillPointsTemplateVO extends TemplateObject {

	/**  刷新神秘商店花费的魔晶数 */
	@ExcelCellBinding(offset = 1)
	protected int crystal;


	public int getCrystal() {
		return this.crystal;
	}

	public void setCrystal(int crystal) {
		if (crystal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 刷新神秘商店花费的魔晶数]crystal的值不得小于0");
		}
		this.crystal = crystal;
	}
	

	@Override
	public String toString() {
		return "ResetSkillPointsTemplateVO[crystal=" + crystal + ",]";

	}
}