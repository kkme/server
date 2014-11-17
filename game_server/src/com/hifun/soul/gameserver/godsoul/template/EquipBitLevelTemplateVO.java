package com.hifun.soul.gameserver.godsoul.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;

/**
 * 神魄装备位等级模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EquipBitLevelTemplateVO extends TemplateObject {

	/** 需要角色等级 */
	@ExcelCellBinding(offset = 1)
	protected int needHumanLevel;

	/** 装备位等级 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.godsoul.template.EquipBitLevel[].class, collectionNumber = "2;3;4;5;6;7;8;9")
	protected com.hifun.soul.gameserver.godsoul.template.EquipBitLevel[] equipBitLevels;

	/** 成功率 */
	@ExcelCellBinding(offset = 10)
	protected int successRate;


	public int getNeedHumanLevel() {
		return this.needHumanLevel;
	}

	public void setNeedHumanLevel(int needHumanLevel) {
		this.needHumanLevel = needHumanLevel;
	}
	
	public com.hifun.soul.gameserver.godsoul.template.EquipBitLevel[] getEquipBitLevels() {
		return this.equipBitLevels;
	}

	public void setEquipBitLevels(com.hifun.soul.gameserver.godsoul.template.EquipBitLevel[] equipBitLevels) {
		if (equipBitLevels == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[装备位等级]equipBitLevels不可以为空");
		}	
		this.equipBitLevels = equipBitLevels;
	}
	
	public int getSuccessRate() {
		return this.successRate;
	}

	public void setSuccessRate(int successRate) {
		this.successRate = successRate;
	}
	

	@Override
	public String toString() {
		return "EquipBitLevelTemplateVO[needHumanLevel=" + needHumanLevel + ",equipBitLevels=" + equipBitLevels + ",successRate=" + successRate + ",]";

	}
}